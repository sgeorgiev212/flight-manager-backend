package com.example.demo.service;

import com.example.demo.model.dto.airport.AirportDto;
import com.example.demo.model.dto.airport.CreateAirportDto;
import com.example.demo.model.dto.airport.CreateAirportResponseDto;
import com.example.demo.model.dto.airport.EditAirportDto;
import com.example.demo.model.entity.Airline;
import com.example.demo.model.entity.Airport;
import com.example.demo.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository;

    public CreateAirportResponseDto registerAirport(CreateAirportDto createAirportDto) {
        validateAirportDto(createAirportDto);
        Airport airport = new Airport(createAirportDto);
        airport = airportRepository.save(airport);
        return new CreateAirportResponseDto(airport);
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    private void validateAirportDto(AirportDto airportDto) {
        if (airportDto.getName().length() < 2) {
            throw new IllegalArgumentException("Airport name must be at least 3 characters long!");
        }

        if (airportDto.getCountry().length() < 3) {
            throw new IllegalArgumentException("Country name must be at least 3 characters long!");
        }
    }

    public Airport editAirport(@RequestBody EditAirportDto editAirportDto) {
        validateAirportDto(editAirportDto);
        Airport airport = findAirportById(editAirportDto.getId());
        airport.setName(editAirportDto.getName());
        airport.setCountry(editAirportDto.getCountry());
        airport = airportRepository.save(airport);

        return airport;
    }

    public Airport findAirportById(int airportId) {
        Optional<Airport> airport = airportRepository.findById(airportId);

        if (airport.isEmpty()) {
            throw new IllegalArgumentException("Airport with id: " + airportId + " was not found!");
        }

        return airport.get();
    }

}
