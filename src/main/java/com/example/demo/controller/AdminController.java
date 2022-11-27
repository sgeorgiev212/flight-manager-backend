package com.example.demo.controller;

import com.example.demo.model.dto.flight.CreateFlightRequestDto;
import com.example.demo.model.dto.flight.CreateFlightResponseDto;
import com.example.demo.model.dto.airline.CreateAirlineRequestDto;
import com.example.demo.model.dto.airline.CreateAirlineResponseDto;
import com.example.demo.model.dto.airport.CreateAirportDto;
import com.example.demo.model.dto.airport.CreateAirportResponseDto;
import com.example.demo.service.AirlineService;
import com.example.demo.service.AirportService;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AirportService airportService;

    @Autowired
    AirlineService airlineService;

    @Autowired
    FlightService flightService;

    @PostMapping("/airport")
    public CreateAirportResponseDto registerAirport(@RequestBody CreateAirportDto createAirportDto){
         return airportService.registerAirport(createAirportDto);
    }

    @PostMapping("/airline")
    public CreateAirlineResponseDto registerAirline(@RequestBody CreateAirlineRequestDto createAirlineRequestDto){
        return airlineService.registerAirline(createAirlineRequestDto);
    }

    @PostMapping("/flight")
    public CreateFlightResponseDto createFLight(@RequestBody CreateFlightRequestDto createFlightRequestDto){
       return flightService.createFlight(createFlightRequestDto);
    }
}
