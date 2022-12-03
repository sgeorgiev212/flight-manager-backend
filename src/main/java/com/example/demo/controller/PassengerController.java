package com.example.demo.controller;

import com.example.demo.model.dto.TicketDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.passenger.LoginRequestDto;
import com.example.demo.model.dto.passenger.PassengerDto;
import com.example.demo.model.dto.passenger.RegisterPassengerRequestDto;
import com.example.demo.model.dto.passenger.RegisterPassengerResponseDto;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
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

    @GetMapping("/{id}/bookings")
    public List<BookingRequestDto> getAllBookingsForUser(@PathVariable int id) {
        return passengerService.getAllBookingsForUser(id);
    }

    @GetMapping("/{id}/tickets")
    public List<TicketDto> getAllTicketsForUser(@PathVariable int id) {
        return passengerService.getAllTicketsForUser(id);
    }

    @DeleteMapping("/{id}/bookings/{bookingId}")
    public String cancelABookingRequestForPassenger(@PathVariable int id, @PathVariable int bookingId) {
        return passengerService.cancelABookingRequestForPassenger(id, bookingId);
    }

}
