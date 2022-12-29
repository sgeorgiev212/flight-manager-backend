package com.example.demo.service;

import com.example.demo.model.dto.airline.AddAirlineReviewDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.passenger.*;
import com.example.demo.model.dto.ticket.PassengerTicketDto;
import com.example.demo.model.dto.travelAgency.AddTravelAgencyReviewDto;
import com.example.demo.model.entity.AirlineReview;
import com.example.demo.model.entity.Passenger;
import com.example.demo.model.entity.TravelAgencyReview;
import com.example.demo.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.demo.util.ServiceUtil.NOT_DEFINED;
import static com.example.demo.util.ServiceUtil.PASSWORD_PATTERN;

@Service
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    BookingRequestService bookingRequestService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    AirlineService airlineService;

    public RegisterPassengerResponseDto registerPassenger(RegisterPassengerRequestDto registerPassengerDto) {
        validateRegisterPassengerDto(registerPassengerDto);
        Passenger passenger = new Passenger(registerPassengerDto);
        passenger.setAddress(NOT_DEFINED);
        passenger = passengerRepository.save(passenger);
        return new RegisterPassengerResponseDto(passenger);

    }

    public PassengerDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Optional<Passenger> passenger = passengerRepository.findByEmail(email);
        if (passenger.isPresent()) {
            Passenger existingPassenger = passenger.get();
            String encryptedPassword = existingPassenger.getPassword();
            PasswordEncoder encoder = new BCryptPasswordEncoder();

            if (!encoder.matches(password, encryptedPassword)) {
                throw new IllegalArgumentException("Wrong password!");
            }
            return new PassengerDto(passenger.get());
        } else {
            throw new IllegalArgumentException("Passenger with email: " + email + " was not found!");
        }
    }

    public PassengerDto editProfile(EditProfileDto editProfileDto, int passengerId) {
        Passenger passenger = findPassengerById(passengerId);
        Passenger updatedPassenger = checkAndApplyChanges(editProfileDto, passenger);
        return new PassengerDto(updatedPassenger);
    }

    public PassengerDto changePassword(ChangePasswordDto changePasswordDto, int passengerId) {
         Passenger passenger = findPassengerById(passengerId);
         passenger = changePassword(changePasswordDto, passenger);
         return new PassengerDto(passenger);
    }

    public List<BookingRequestDto> getAllBookingsForUser(int id) {
        Passenger passenger = findPassengerById(id);

        return passenger.getBookingRequests().stream()
                .map(bookingRequest -> new BookingRequestDto(bookingRequest))
                .collect(Collectors.toList());
    }

    public List<PassengerTicketDto> getAllTicketsForUser(int id) {
        Passenger passenger = findPassengerById(id);

        return passenger.getTickets().stream()
                .map(ticket -> new PassengerTicketDto(ticket))
                .collect(Collectors.toList());
    }

    public String cancelABookingRequestForPassenger(int passengerId, int bookingId) {
        findPassengerById(passengerId);
        return bookingRequestService.cancelABookingForPassenger(passengerId, bookingId);
    }

    public TravelAgencyReview addReviewForTravelAgency(AddTravelAgencyReviewDto addTravelAgencyReviewDto) {
        Passenger passenger = findPassengerById(addTravelAgencyReviewDto.getReviewerId());
        return travelAgencyService.addReviewForTravelAgency(addTravelAgencyReviewDto, passenger);
    }

    public AirlineReview addReviewForAirline(AddAirlineReviewDto addAirlineReviewDto) {
        Passenger passenger = findPassengerById(addAirlineReviewDto.getReviewerId());
        return airlineService.addReviewForAirline(addAirlineReviewDto, passenger);
    }

    public Passenger findPassengerById(int passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (passenger.isEmpty()) {
            throw new IllegalArgumentException("Passenger with id: " + passengerId + " was not found!");
        }

        return passenger.get();
    }

    private void validateRegisterPassengerDto(RegisterPassengerRequestDto registerPassengerRequestDto) {
        if (registerPassengerRequestDto.getFirstName().length() < 2) {
            throw new IllegalArgumentException("First name must be at least 2 characters long!");
        }

        if (registerPassengerRequestDto.getLastName().length() < 2) {
            throw new IllegalArgumentException("Last name must be at least 2 characters long!");
        }

        String password = registerPassengerRequestDto.getPassword();
        String confirmPassword = registerPassengerRequestDto.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("Password must be between 8 and 20 characters long!");
        }

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Password must contain at least one lowercase character [a-z],\n" +
                    "at least one uppercase character [A-Z],\n" +
                    "at least one special character like ! @ # & ( ),\n" +
                    "");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        registerPassengerRequestDto.setPassword(encodedPassword);
    }


    private RegisterPassengerResponseDto getRegisterResponse(Passenger passenger) {
        return new RegisterPassengerResponseDto(passenger);
    }

    private Passenger checkAndApplyChanges(EditProfileDto editProfileDto, Passenger passenger) {
        String firstName = editProfileDto.getFirstName();
        if (firstName.length() == 1) {
            throw new IllegalArgumentException("First name must be at least 2 characters long!");
        } else {
            if (firstName.length() >= 2) {
                passenger.setFirstName(firstName);
            }
        }

        String lastName = editProfileDto.getLastName();
        if (lastName.length() == 1) {
            throw new IllegalArgumentException("Last name must be at least 2 characters long!");
        } else {
            if (lastName.length() >= 2) {
                passenger.setLastName(lastName);
            }
        }

        String email = editProfileDto.getEmail();
        if (email.length() == 1) {
            throw new IllegalArgumentException("Email name must be at least 10 characters long!");
        } else {
            if (email.length() >= 10) {
                passenger.setEmail(email);
            }
        }

        String address = editProfileDto.getAddress();
        if (address.length() == 1) {
            throw new IllegalArgumentException("Address name must be at least 10 characters long!");
        } else {
            if (address.length() >= 10) {
                passenger.setAddress(address);
            }
        }

        return passengerRepository.save(passenger);
    }

    private Passenger changePassword(ChangePasswordDto changePasswordDto, Passenger passenger) {
        String password = changePasswordDto.getPassword();
        String newPassword = changePasswordDto.getNewPassword();

        String encryptedPassword = passenger.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, encryp tedPassword)) {
            throw new IllegalArgumentException("Wrong current password!");
        }

        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = passwordPattern.matcher(newPassword);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("New password must contain at least one lowercase character [a-z],\n" +
                    "at least one uppercase character [A-Z],\n" +
                    "at least one special character like ! @ # & ( ),\n" +
                    "");
        }

        String encodedNewPassword = encoder.encode(newPassword);
        passenger.setPassword(encodedNewPassword);
        passenger = passengerRepository.save(passenger);

        return passenger;
    }

}
