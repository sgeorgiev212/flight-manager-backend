package com.example.demo.controller;

import com.example.demo.model.dto.airport.EditAirportDto;
import com.example.demo.model.entity.Airport;
import com.example.demo.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
@CrossOrigin(origins="http://localhost:8080")
public class AirportController {

    @Autowired
    AirportService airportService;

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @PutMapping()
    public Airport editAirport(@RequestBody EditAirportDto editAirportDto) {
          return airportService.editAirport(editAirportDto);
    }

}
