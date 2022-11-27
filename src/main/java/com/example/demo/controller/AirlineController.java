package com.example.demo.controller;

import com.example.demo.model.dto.airline.GetAllFlightsForAirlineByDateDto;
import com.example.demo.model.dto.flight.CreateFlightRequestDto;
import com.example.demo.model.dto.flight.CreateFlightResponseDto;
import com.example.demo.model.dto.flight.EditFlightDto;
import com.example.demo.model.dto.flight.FLightDto;
import com.example.demo.service.AirlineService;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airline")
public class AirlineController {

    @Autowired
    AirlineService airlineService;

    @Autowired
    FlightService flightService;

    @GetMapping("/{airlineId}/flights")
    public List<FLightDto> getAllAvailableFlights(@PathVariable int airlineId) {
        return airlineService.getAllFlightsForAirline(airlineId);
    }

    @PostMapping("flights")
    public List<FLightDto> getAllFLightsFromDate(@RequestBody GetAllFlightsForAirlineByDateDto getAllFlightsForAirlineByDateDto) {
        return airlineService.getAllFLightsForAirlineFromDate(getAllFlightsForAirlineByDateDto);
    }

    @PostMapping("/flight")
    public CreateFlightResponseDto createFLight(@RequestBody CreateFlightRequestDto createFlightRequestDto) {
        return flightService.createFlight(createFlightRequestDto);
    }

    @PutMapping("/flight")
    public FLightDto editFlight(@RequestBody EditFlightDto editFlightDto) {
        return flightService.editFlight(editFlightDto);
    }

    @PutMapping("/flights/{flightId}")
    public FLightDto cancelFlight(@PathVariable int flightId){
        return flightService.cancelFlight(flightId);
    }
}
