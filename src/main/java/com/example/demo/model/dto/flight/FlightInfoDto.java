package com.example.demo.model.dto.flight;

import com.example.demo.model.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightInfoDto {

    private int flightId;

    private Timestamp takeoffTime;

    private Timestamp landTime;

    private String takeoffAirport;

    private String landAirport;

    private String airline;

    private String status;

    public FlightInfoDto(Flight flight) {
        this.flightId = flight.getId();
        this.takeoffTime = flight.getTakeoffTime();
        this.landTime = flight.getLandTime();
        this.takeoffAirport = flight.getTakeoffAirport().getName();
        this.landAirport = flight.getLandAirport().getName();
        this.airline = flight.getAirline().getName();
        this.status = flight.getStatus();
    }

}
