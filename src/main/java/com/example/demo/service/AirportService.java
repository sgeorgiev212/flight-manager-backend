package com.example.demo.service;

import com.example.demo.model.dto.airport.CreateAirportDto;
import com.example.demo.model.dto.airport.CreateAirportResponseDto;
import com.example.demo.model.entity.Airport;
import com.example.demo.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository;

    public CreateAirportResponseDto registerAirport(CreateAirportDto createAirportDto){
        validateAirportRegistration(createAirportDto);
        Airport airport = new Airport(createAirportDto);
        airport = airportRepository.save(airport);
        return new CreateAirportResponseDto(airport);
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    private void validateAirportRegistration(CreateAirportDto createAirportDto)
    {
        if (createAirportDto.getName().length() < 3)
        {
            throw new IllegalArgumentException("Airport name must be at least 3 characters long!");
        }

        if(createAirportDto.getCountry().length() < 3)
        {
            throw new IllegalArgumentException("Country name must be at least 3 characters long!");
        }
    }

}
