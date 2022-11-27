package com.example.demo.service;

import com.example.demo.model.dto.airline.CreateAirlineRequestDto;
import com.example.demo.model.dto.airline.CreateAirlineResponseDto;
import com.example.demo.model.dto.airline.GetAllFlightsForAirlineByDateDto;
import com.example.demo.model.dto.flight.FLightDto;
import com.example.demo.model.entity.Airline;
import com.example.demo.model.entity.Flight;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public CreateAirlineResponseDto registerAirline(CreateAirlineRequestDto createAirlineRequestDto) {
        validateAirlineRegistration(createAirlineRequestDto);
        Airline airline = new Airline(createAirlineRequestDto);
        airline.setStatus(CREATED_STATUS);
        airline = airlineRepository.save(airline);
        return new CreateAirlineResponseDto(airline);
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

    private void validateAirlineRegistration(CreateAirlineRequestDto createAirlineRequestDto) {
        if (createAirlineRequestDto.getName().length() < 3) {
            throw new IllegalArgumentException("Airline name must be at least 3 characters long!");
        }

        if (createAirlineRequestDto.getAddress().length() < 10) {
            throw new IllegalArgumentException("Airline address must be at least 10 characters long!");
        }
    }
}
