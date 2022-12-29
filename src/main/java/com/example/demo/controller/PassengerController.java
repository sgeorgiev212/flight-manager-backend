package com.example.demo.controller;

import com.example.demo.model.dto.airline.AddAirlineReviewDto;
import com.example.demo.model.dto.passenger.*;
import com.example.demo.model.dto.ticket.PassengerTicketDto;
import com.example.demo.model.dto.ticket.TicketDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.travelAgency.AddTravelAgencyReviewDto;
import com.example.demo.model.entity.AirlineReview;
import com.example.demo.model.entity.TravelAgencyReview;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:8080")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @PostMapping
    public RegisterPassengerResponseDto registerUser(@RequestBody RegisterPassengerRequestDto registerPassengerDto) {
        return passengerService.registerPassenger(registerPassengerDto);
    }

    @PostMapping("/login")
    public PassengerDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return passengerService.login(loginRequestDto);
    }

    @PutMapping("/{id}")
    public PassengerDto editProfile(@RequestBody EditProfileDto editProfileDto, @PathVariable int id) {
        return passengerService.editProfile(editProfileDto, id);
    }

    @PutMapping("/{id}/password")
    public PassengerDto changePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable int id) {
        return passengerService.changePassword(changePasswordDto, id);
    }

    @GetMapping("/{id}/bookings")
    public List<BookingRequestDto> getAllBookingsForUser(@PathVariable int id) {
        return passengerService.getAllBookingsForUser(id);
    }

    @GetMapping("/{id}/tickets")
    public List<PassengerTicketDto> getAllTicketsForUser(@PathVariable int id) {
        return passengerService.getAllTicketsForUser(id);
    }

    @DeleteMapping("/{id}/bookings/{bookingId}")
    public String cancelABookingRequestForPassenger(@PathVariable int id, @PathVariable int bookingId) {
        return passengerService.cancelABookingRequestForPassenger(id, bookingId);
    }

    @PostMapping("/reviews/agency")
    public TravelAgencyReview addReviewForTravelAgency(@RequestBody AddTravelAgencyReviewDto addTravelAgencyReviewDto) {
        return passengerService.addReviewForTravelAgency(addTravelAgencyReviewDto);
    }

    @PostMapping("/reviews/airline")
    public AirlineReview addReviewForAirline(@RequestBody AddAirlineReviewDto airlineReviewDto) {
        return passengerService.addReviewForAirline(airlineReviewDto);
    }

    @GetMapping("/{id}")
    public PassengerDto findPassengerById(@PathVariable int id) {
        return new PassengerDto(passengerService.findPassengerById(id));
    }

}
