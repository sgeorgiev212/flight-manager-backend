package com.example.demo.service;

import com.example.demo.model.dto.airline.CreateAirlineRequestDto;
import com.example.demo.model.dto.airline.CreateAirlineResponseDto;
import com.example.demo.model.dto.airport.CreateAirportDto;
import com.example.demo.model.entity.Airline;
import com.example.demo.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.util.ServiceUtil.CREATED_STATUS;

@Service
public class AirlineService {

    @Autowired
    AirlineRepository airlineRepository;

    public CreateAirlineResponseDto registerAirline(CreateAirlineRequestDto createAirlineRequestDto){
        validateAirlineRegistration(createAirlineRequestDto);
        Airline airline = new Airline(createAirlineRequestDto);
        airline.setStatus(CREATED_STATUS);
        airline = airlineRepository.save(airline);
        return new CreateAirlineResponseDto(airline);
    }

    private void validateAirlineRegistration(CreateAirlineRequestDto createAirlineRequestDto)
    {
        if (createAirlineRequestDto.getName().length() < 3){
            throw new IllegalArgumentException("Airline name must be at least 3 characters long!");
        }

        if(createAirlineRequestDto.getAddress().length() < 10){
            throw new IllegalArgumentException("Airline address must be at least 10 characters long!");
        }
    }
}
