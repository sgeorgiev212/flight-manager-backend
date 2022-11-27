package com.example.demo.service;

import com.example.demo.model.dto.CreateFlightRequestDto;
import com.example.demo.model.dto.CreateFlightResponseDto;
import com.example.demo.model.entity.Airline;
import com.example.demo.model.entity.Airport;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.util.ServiceUtil.ACTIVE_FLIGHT_STATUS;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirlineRepository airlineRepository;

    public CreateFlightResponseDto createFlight(CreateFlightRequestDto createFlightRequestDto) {
        int takeOffAirportId = createFlightRequestDto.getTakeoffAirportId();
        int landAirportId = createFlightRequestDto.getLandAirportId();
        int airlineId = createFlightRequestDto.getAirlineId();

        Optional<Airport> takeoffAirport = airportRepository.findById(takeOffAirportId);
        Optional<Airport> landAirport = airportRepository.findById(landAirportId);
        Optional<Airline> airline = airlineRepository.findById(airlineId);

        Flight flight = new Flight();
        flight.setTakeoffTime(createFlightRequestDto.getTakeoffTime());
        flight.setLandTime(createFlightRequestDto.getLandTime());
        flight.setTakeoffAirport(takeoffAirport.get());
        flight.setLandAirport(landAirport.get());
        flight.setAirline(airline.get());
        flight.setStatus(ACTIVE_FLIGHT_STATUS);
        flight = flightRepository.save(flight);

        return new CreateFlightResponseDto(flight);
    }
}
