package com.example.demo.service;

import com.example.demo.model.dto.flight.CreateFlightRequestDto;
import com.example.demo.model.dto.flight.CreateFlightResponseDto;
import com.example.demo.model.dto.flight.EditFlightDto;
import com.example.demo.model.dto.flight.FLightDto;
import com.example.demo.model.entity.Airline;
import com.example.demo.model.entity.Airport;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.example.demo.util.ServiceUtil.ACTIVE_FLIGHT_STATUS;
import static com.example.demo.util.ServiceUtil.CANCELED_FLIGHT_STATUS;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirlineRepository airlineRepository;

    public CreateFlightResponseDto createFlight(CreateFlightRequestDto createFlightRequestDto) {
        checkIfFlightIsRegistered(createFlightRequestDto);
        Flight flight = validateAndCreateFlightFromRequest(createFlightRequestDto);
        flight = flightRepository.save(flight);
        return new CreateFlightResponseDto(flight);
    }

    public FLightDto editFlight(@RequestBody EditFlightDto editFlightDto){
        int flightId = editFlightDto.getFlightId();
        if (flightRepository.findById(flightId).isEmpty()){
            throw new IllegalArgumentException("Flight with id: " + flightId + " was not found!");
        }
        Flight flight = flightRepository.findById(flightId).get();

        Timestamp takeoffTime = editFlightDto.getTakeoffTime();
        Timestamp landTime = editFlightDto.getLandTime();
        if(takeoffTime != null && landTime!= null){
        validateTakeoffAndLandingTime(takeoffTime,landTime);
        }

        if(takeoffTime != null)
        {
            validateAndSetNewTakeoffTime(takeoffTime, flight);
        }

        if(landTime != null){
            validateAndSetNewLandTime(landTime, flight);
        }

        int takeoffAirportId = editFlightDto.getTakeoffAirportId();
        int landAirportId = editFlightDto.getLandAirportId();
        if(takeoffAirportId == landAirportId){
            throw new IllegalArgumentException("Takeoff airport id must not be equal to land airport id!");
        }

        if(takeoffAirportId != 0){
            Airport takeOffAirport = validateAirportId(takeoffAirportId);
            flight.setTakeoffAirport(takeOffAirport);

        }

        if(landAirportId != 0){
            Airport landAirport = validateAirportId(landAirportId);
            flight.setLandAirport(landAirport);
        }
        flight = flightRepository.save(flight);
        return new FLightDto(flight);
    }

    public FLightDto cancelFlight(int flightId){
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isEmpty()){
            throw new IllegalArgumentException("Takeoff airport id must not be equal to land airport id!");
        }

        Flight existingFlight = flight.get();
        existingFlight.setStatus(CANCELED_FLIGHT_STATUS);
        existingFlight = flightRepository.save(existingFlight);
        return new FLightDto(existingFlight);
    }

    private Flight validateAndCreateFlightFromRequest(CreateFlightRequestDto createFlightRequestDto){
        int takeOffAirportId = createFlightRequestDto.getTakeoffAirportId();
        int landAirportId = createFlightRequestDto.getLandAirportId();
        int airlineId = createFlightRequestDto.getAirlineId();

        Airport takeoffAirport  = validateAirportId(takeOffAirportId);
        Airport landAirport = validateAirportId(landAirportId);

        if(takeOffAirportId == landAirportId){
            throw new IllegalArgumentException("Takeoff airport id must not be equal to land airport id!");
        }

//        Optional<Airport> takeoffAirport = airportRepository.findById(takeOffAirportId);
//        if(takeoffAirport.isEmpty()) {
//            throw new IllegalArgumentException("Airport with id: " + takeOffAirportId + " was not found!");
//        }
//
//        Optional<Airport> landAirport = airportRepository.findById(landAirportId);
//        if(landAirport.isEmpty()) {
//            throw new IllegalArgumentException("Airport with id: " + landAirportId + " was not found!");
//        }

        Optional<Airline> airline = airlineRepository.findById(airlineId);
        if(airline.isEmpty()){
            throw new IllegalArgumentException("Airline with id: " + airlineId + " was not found!");
        }

        Timestamp takeoffTime = createFlightRequestDto.getTakeoffTime();
        Timestamp landTime = createFlightRequestDto.getLandTime();
        validateTakeoffAndLandingTime(takeoffTime,landTime);
//        if(takeoffTime.equals(landTime) || takeoffTime.after(landTime)){
//            throw new IllegalArgumentException("Takeoff time must be before land time");
//        }
//
//        takeoffTime.setTime(takeoffTime.getTime() + TimeUnit.MINUTES.toMillis(30));
//        if(takeoffTime.after(landTime)){
//            throw new IllegalArgumentException("Flight duration must be at least 30 minutes.\n " +
//                    "Enter correct takeoff and land time!");
//        }
//        takeoffTime.setTime(takeoffTime.getTime() - TimeUnit.MINUTES.toMillis(30));

        Flight flight = new Flight();
        flight.setTakeoffTime(createFlightRequestDto.getTakeoffTime());
        flight.setLandTime(createFlightRequestDto.getLandTime());
        flight.setTakeoffAirport(takeoffAirport);
        flight.setLandAirport(landAirport);
        flight.setAirline(airline.get());
        flight.setStatus(ACTIVE_FLIGHT_STATUS);

        return flight;
    }

    private void validateTakeoffAndLandingTime(Timestamp takeoffTime, Timestamp landTime){
        if(takeoffTime.equals(landTime) || takeoffTime.after(landTime)){
            throw new IllegalArgumentException("Takeoff time must be before land time");
        }

        takeoffTime.setTime(takeoffTime.getTime() + TimeUnit.MINUTES.toMillis(30));
        if(takeoffTime.after(landTime)){
            throw new IllegalArgumentException("Flight duration must be at least 30 minutes.\n " +
                    "Enter correct takeoff and land time!");
        }
        takeoffTime.setTime(takeoffTime.getTime() - TimeUnit.MINUTES.toMillis(30));
    }

    private Airport validateAirportId(int airportId){
        Optional<Airport> airport = airportRepository.findById(airportId);
        if(airport.isEmpty()) {
            throw new IllegalArgumentException("Airport with id: " + airportId + " was not found!");
        }

        return airport.get();
    }

    private void checkIfFlightIsRegistered(CreateFlightRequestDto createFlightRequestDto){
        Timestamp takeoffTime = createFlightRequestDto.getTakeoffTime();
        Timestamp landTime = createFlightRequestDto.getLandTime();
        int takeOffAirportId = createFlightRequestDto.getTakeoffAirportId();
        int landAirportId = createFlightRequestDto.getLandAirportId();
        int airlineId = createFlightRequestDto.getAirlineId();

        Flight flight = flightRepository.checkIfFLightIsAlreadyRegistered(takeoffTime, landTime, takeOffAirportId, landAirportId, airlineId);
        if(flight != null){
            throw new IllegalArgumentException("The flight is already registered");
        }
    }

    private void validateAndSetNewTakeoffTime(Timestamp takeoffTime, Flight flight) {
        Timestamp landTime = flight.getLandTime();
        validateTakeoffAndLandingTime(takeoffTime,landTime);
        flight.setTakeoffTime(takeoffTime);
    }

    private void validateAndSetNewLandTime(Timestamp landTime, Flight flight) {
        Timestamp takeoffTime = flight.getTakeoffTime();
        validateTakeoffAndLandingTime(takeoffTime,landTime);
        flight.setLandTime(landTime);
    }
}
