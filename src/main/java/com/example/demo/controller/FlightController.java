package com.example.demo.controller;

import com.example.demo.model.dto.flight.BookFlightRequestDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping
    public BookingRequestDto bookFlight(@RequestBody BookFlightRequestDto bookFlightRequestDto) {
        return flightService.bookFlight(bookFlightRequestDto);
    }

}
