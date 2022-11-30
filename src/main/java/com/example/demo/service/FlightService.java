package com.example.demo.service;

import com.example.demo.model.dto.TicketDto;
import com.example.demo.model.dto.flight.*;
import com.example.demo.model.entity.*;
import com.example.demo.repository.*;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    TravelAgencyRepository travelAgencyRepository;

    @Autowired
    BookingRequestRepository bookingRequestRepository;

    public CreateFlightResponseDto createFlight(CreateFlightRequestDto createFlightRequestDto) {
        checkIfFlightIsRegistered(createFlightRequestDto);
        Flight flight = validateAndCreateFlightFromRequest(createFlightRequestDto);
        flight = flightRepository.save(flight);
        return new CreateFlightResponseDto(flight);
    }

    public FLightDto editFlight(@RequestBody EditFlightDto editFlightDto) {
        int flightId = editFlightDto.getFlightId();
        if (flightRepository.findById(flightId).isEmpty()) {
            throw new IllegalArgumentException("Flight with id: " + flightId + " was not found!");
        }
        Flight flight = flightRepository.findById(flightId).get();

        Timestamp takeoffTime = editFlightDto.getTakeoffTime();
        Timestamp landTime = editFlightDto.getLandTime();
        if (takeoffTime != null && landTime != null) {
            validateTakeoffAndLandingTime(takeoffTime, landTime);
        }

        if (takeoffTime != null) {
            validateAndSetNewTakeoffTime(takeoffTime, flight);
        }

        if (landTime != null) {
            validateAndSetNewLandTime(landTime, flight);
        }

        int takeoffAirportId = editFlightDto.getTakeoffAirportId();
        int landAirportId = editFlightDto.getLandAirportId();
        if (takeoffAirportId == landAirportId) {
            throw new IllegalArgumentException("Takeoff airport id must not be equal to land airport id!");
        }

        if (takeoffAirportId != 0) {
            Airport takeOffAirport = validateAirportId(takeoffAirportId);
            flight.setTakeoffAirport(takeOffAirport);

        }

        if (landAirportId != 0) {
            Airport landAirport = validateAirportId(landAirportId);
            flight.setLandAirport(landAirport);
        }
        flight = flightRepository.save(flight);
        return new FLightDto(flight);
    }

    public FLightDto cancelFlight(int flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        if (flight.isEmpty()) {
            throw new IllegalArgumentException("Takeoff airport id must not be equal to land airport id!");
        }

        Flight existingFlight = flight.get();
        existingFlight.setStatus(CANCELED_FLIGHT_STATUS);
        existingFlight = flightRepository.save(existingFlight);
        return new FLightDto(existingFlight);
    }

    public BookingRequestDto bookFlight(BookFlightRequestDto bookFlightRequestDto) {
        validateBookFLightRequestDto(bookFlightRequestDto);

        BookingRequest bookingRequest = new BookingRequest();

        Passenger passenger = passengerRepository.findById(bookFlightRequestDto.getPassengerId()).get();
        bookingRequest.setPassengerHasBooking(passenger);

        if (bookFlightRequestDto.getAgencyId() != 0) {
            TravelAgency travelAgency = travelAgencyRepository.findById(bookFlightRequestDto.getAgencyId()).get();
            bookingRequest.setTravelAgency(travelAgency);
        }

        Airline airline = airlineRepository.findById(bookFlightRequestDto.getAirlineId()).get();
        bookingRequest.setAirline(airline);

        Flight flight = flightRepository.findById(bookFlightRequestDto.getFlightId()).get();
        bookingRequest.setFlight(flight);

        bookingRequest = bookingRequestRepository.save(bookingRequest);
        return new BookingRequestDto(bookingRequest);
    }

    public List<TicketDto> getBookedTicketsForFlight(int id) {
        Optional<Flight> flightFromDB = flightRepository.findById(id);
        if (flightFromDB.isEmpty()) {
            throw new IllegalArgumentException("FLight with id: " + id + " was not found!");
        }

        Flight flight = flightFromDB.get();
        return flight.getTickets().stream()
                .map(ticket -> new TicketDto(ticket))
                .collect(Collectors.toList());
    }

    public Flight findFlightById(int flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        return (flight.isPresent()) ? flight.get() : null;
    }

    private void validateBookFLightRequestDto(BookFlightRequestDto bookFlightRequestDto) {
        int passengerId = bookFlightRequestDto.getPassengerId();

        if (passengerRepository.findById(passengerId).isEmpty()) {
            throw new IllegalArgumentException("Passenger with id: " + passengerId + " was not found!");
        }

        int agencyId = bookFlightRequestDto.getAgencyId();
        if (agencyId != 0 && travelAgencyRepository.findById(agencyId).isEmpty()) {
            throw new IllegalArgumentException("Travel agency with id: " + agencyId + " was not found!");
        }

        int airlineId = bookFlightRequestDto.getAirlineId();
        if (airlineRepository.findById(airlineId).isEmpty()) {
            throw new IllegalArgumentException("Airline with id: " + airlineId + " was not found!");
        }

        int flightId = bookFlightRequestDto.getFlightId();
        if (flightRepository.findById(flightId).isEmpty()) {
            throw new IllegalArgumentException("Fight with id: " + flightId + " was not found!");
        }
    }

    private Flight validateAndCreateFlightFromRequest(CreateFlightRequestDto createFlightRequestDto) {
        int takeOffAirportId = createFlightRequestDto.getTakeoffAirportId();
        int landAirportId = createFlightRequestDto.getLandAirportId();
        int airlineId = createFlightRequestDto.getAirlineId();

        Airport takeoffAirport = validateAirportId(takeOffAirportId);
        Airport landAirport = validateAirportId(landAirportId);

        if (takeOffAirportId == landAirportId) {
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
        if (airline.isEmpty()) {
            throw new IllegalArgumentException("Airline with id: " + airlineId + " was not found!");
        }

        Timestamp takeoffTime = createFlightRequestDto.getTakeoffTime();
        Timestamp landTime = createFlightRequestDto.getLandTime();
        validateTakeoffAndLandingTime(takeoffTime, landTime);
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

    private void validateTakeoffAndLandingTime(Timestamp takeoffTime, Timestamp landTime) {
        if (takeoffTime.equals(landTime) || takeoffTime.after(landTime)) {
            throw new IllegalArgumentException("Takeoff time must be before land time");
        }

        takeoffTime.setTime(takeoffTime.getTime() + TimeUnit.MINUTES.toMillis(30));
        if (takeoffTime.after(landTime)) {
            throw new IllegalArgumentException("Flight duration must be at least 30 minutes.\n " +
                    "Enter correct takeoff and land time!");
        }
        takeoffTime.setTime(takeoffTime.getTime() - TimeUnit.MINUTES.toMillis(30));
    }

    private Airport validateAirportId(int airportId) {
        Optional<Airport> airport = airportRepository.findById(airportId);
        if (airport.isEmpty()) {
            throw new IllegalArgumentException("Airport with id: " + airportId + " was not found!");
        }

        return airport.get();
    }

    private void checkIfFlightIsRegistered(CreateFlightRequestDto createFlightRequestDto) {
        Timestamp takeoffTime = createFlightRequestDto.getTakeoffTime();
        Timestamp landTime = createFlightRequestDto.getLandTime();
        int takeOffAirportId = createFlightRequestDto.getTakeoffAirportId();
        int landAirportId = createFlightRequestDto.getLandAirportId();
        int airlineId = createFlightRequestDto.getAirlineId();

        Flight flight = flightRepository.checkIfFLightIsAlreadyRegistered(takeoffTime, landTime, takeOffAirportId, landAirportId, airlineId);
        if (flight != null) {
            throw new IllegalArgumentException("The flight is already registered");
        }
    }

    private void validateAndSetNewTakeoffTime(Timestamp takeoffTime, Flight flight) {
        Timestamp landTime = flight.getLandTime();
        validateTakeoffAndLandingTime(takeoffTime, landTime);
        flight.setTakeoffTime(takeoffTime);
    }

    private void validateAndSetNewLandTime(Timestamp landTime, Flight flight) {
        Timestamp takeoffTime = flight.getTakeoffTime();
        validateTakeoffAndLandingTime(takeoffTime, landTime);
        flight.setLandTime(landTime);
    }
}
