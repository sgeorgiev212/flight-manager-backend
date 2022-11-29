package com.example.demo.service;

import com.example.demo.model.dto.airline.CreateAirlineRequestDto;
import com.example.demo.model.dto.airline.AirlineDto;
import com.example.demo.model.dto.airline.GetAllFlightsForAirlineByDateDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.flight.FLightDto;
import com.example.demo.model.dto.travelAgency.TravelAgencyDto;
import com.example.demo.model.entity.Airline;
import com.example.demo.model.entity.BookingRequest;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.BookingRequestRepository;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.util.ServiceUtil.CREATED_STATUS;

@Service
public class AirlineService {

    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    BookingRequestRepository bookingRequestRepository;

    public AirlineDto registerAirline(CreateAirlineRequestDto createAirlineRequestDto) {
        validateAirlineRegistration(createAirlineRequestDto);
        Airline airline = new Airline(createAirlineRequestDto);
        airline.setStatus(CREATED_STATUS);
        airline = airlineRepository.save(airline);
        return new AirlineDto(airline);
    }

    public List<FLightDto> getAllFlightsForAirline(int airlineId) {
        List<FLightDto> result;
        List<Flight> flights = flightRepository.findAllByAirlineId(airlineId);
        result = flights.stream()
                .map(flight -> new FLightDto(flight))
                .collect(Collectors.toList());

        return result;
    }

    public List<FLightDto> getAllFLightsForAirlineFromDate(GetAllFlightsForAirlineByDateDto getAllFlightsForAirlineByDateDto) {
        int airlineId = getAllFlightsForAirlineByDateDto.getAirlineId();
        LocalDate takeoffDate = getAllFlightsForAirlineByDateDto.getTakeoffDate();
        Timestamp takeOffStartDateForDb = Timestamp.valueOf(takeoffDate.atStartOfDay());
        Timestamp takeOffEndDateForDb = getEndDateAndTimeForCurrentDay(takeOffStartDateForDb);
        List<Flight> flights = flightRepository.findAllByAirlineIdAndTakeoffTime(airlineId, takeOffStartDateForDb, takeOffEndDateForDb);
        if (flights.isEmpty()) {
            throw new IllegalArgumentException("No flights were found for airline with id: " + airlineId + "" +
                    " and take off date " + takeoffDate);
        }
        return flights.stream()
                .map(flight -> new FLightDto(flight))
                .collect(Collectors.toList());
    }

    public Timestamp getEndDateAndTimeForCurrentDay(Timestamp takeoffDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(takeoffDate.getTime());
        calendar.add(Calendar.HOUR, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);
        Timestamp endDateAndTimeForCurrentDay = new Timestamp(calendar.getTime().getTime());
        return endDateAndTimeForCurrentDay;
    }

    public List<AirlineDto> getAllAirlines() {
        List<Airline> registeredAgencies = airlineRepository.findAllCreatedAirlines();
        if (registeredAgencies == null || registeredAgencies.size() == 0) {
            throw new IllegalArgumentException("No created agencies found!");
        }

        return registeredAgencies.stream()
                .map(airline -> new AirlineDto(airline))
                .collect(Collectors.toList());
    }

    public List<BookingRequestDto> getAllBookingRequestsForAirline(int airlineId) {
        if (airlineRepository.findById(airlineId).get() == null) {
            throw new IllegalArgumentException("Airline with id: " + airlineId + " was not found!");
        }

        return bookingRequestRepository.findByAirlineId(airlineId).stream()
                .map(bookingRequest -> new BookingRequestDto(bookingRequest))
                .collect(Collectors.toList());
    }

    private void validateAirlineRegistration(CreateAirlineRequestDto createAirlineRequestDto) {
        String airlineName = createAirlineRequestDto.getName();
        String airlineAddress = createAirlineRequestDto.getAddress();

        if (airlineName == null || airlineName.length() < 3) {
            throw new IllegalArgumentException("Airline name must be at least 3 characters long!");
        }

        if (airlineAddress == null || airlineAddress.length() < 10) {
            throw new IllegalArgumentException("Airline address must be at least 10 characters long!");
        }

        if (airlineRepository.findByName(airlineName) != null) {
            throw new IllegalArgumentException("Airline with name: " + airlineName + " already exists!");
        }
    }
}
