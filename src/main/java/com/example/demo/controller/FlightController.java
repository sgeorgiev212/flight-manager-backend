package com.example.demo.controller;

import com.example.demo.model.dto.flight.FLightDto;
import com.example.demo.model.dto.flight.FlightInfoDto;
import com.example.demo.model.dto.ticket.TicketDto;
import com.example.demo.model.dto.flight.BookFlightRequestDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.passenger.PassengerDto;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins="http://localhost:8080")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping
    public BookingRequestDto bookFlight(@RequestBody BookFlightRequestDto bookFlightRequestDto) {
        return flightService.bookFlight(bookFlightRequestDto);
    }

    @GetMapping("/{id}/tickets")
    public List<TicketDto> getBookedTicketsForFlight(@PathVariable int id) {
        return flightService.getBookedTicketsForFlight(id);
    }

    @GetMapping("/{id}/passengers")
    public List<PassengerDto> getAllPassengersForFlight(@PathVariable int id) {
        return flightService.getAllPassengersForFlight(id);
    }

    @GetMapping()
    public List<FlightInfoDto> getAllFLights() {
        return flightService.getAllFLights();
    }
}
