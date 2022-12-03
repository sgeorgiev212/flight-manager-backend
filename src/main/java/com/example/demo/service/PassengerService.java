package com.example.demo.service;

import com.example.demo.model.dto.TicketDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.passenger.LoginRequestDto;
import com.example.demo.model.dto.passenger.LoginResponseDto;
import com.example.demo.model.dto.passenger.RegisterPassengerRequestDto;
import com.example.demo.model.dto.passenger.RegisterPassengerResponseDto;
import com.example.demo.model.entity.Passenger;
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

import static com.example.demo.util.ServiceUtil.PASSWORD_PATTERN;

@Service
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    BookingRequestService bookingRequestService;

    public RegisterPassengerResponseDto registerPassenger(RegisterPassengerRequestDto registerPassengerDto) {
        validateRegisterPassengerDto(registerPassengerDto);
        Passenger passenger = new Passenger(registerPassengerDto);
        passenger = passengerRepository.save(passenger);
        return new RegisterPassengerResponseDto(passenger);

    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
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
            return new LoginResponseDto(passenger.get());
        } else {
            throw new IllegalArgumentException("Passenger with email: " + email + " was not found!");
        }
    }

    public List<BookingRequestDto> getAllBookingsForUser(int id) {
        Passenger passenger = findPassengerById(id);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger with id: " + id + " was not found!");
        }

        return passenger.getBookingRequests().stream()
                .map(bookingRequest -> new BookingRequestDto(bookingRequest))
                .collect(Collectors.toList());
    }

    public List<TicketDto> getAllTicketsForUser(int id) {
        Passenger passenger = findPassengerById(id);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger with id: " + id + " was not found!");
        }

        return passenger.getTickets().stream()
                .map(ticket -> new TicketDto(ticket))
                .collect(Collectors.toList());
    }

    public String cancelABookingRequestForPassenger(int passengerId, int bookingId) {
        Passenger passenger = findPassengerById(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger with id: " + passengerId + " was not found!");
        }

        return bookingRequestService.cancelABookingForPassenger(passengerId, bookingId);
    }

    public Passenger findPassengerById(int passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        return (passenger.isPresent()) ? passenger.get() : null;
    }

    private void validateRegisterPassengerDto(RegisterPassengerRequestDto registerPassengerRequestDto) {
        if (registerPassengerRequestDto.getFirstName().length() < 2) {
            throw new IllegalArgumentException("First name must be at least 2 characters long!");
        }

        if (registerPassengerRequestDto.getLastName().length() < 2) {
            throw new IllegalArgumentException("Last name must be at least 2 characters long!");
        }

        String password = registerPassengerRequestDto.getPassword();
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

}
