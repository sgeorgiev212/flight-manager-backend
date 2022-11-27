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
public class FLightDto {

    private int flightId;

    private Timestamp takeoffTime;

    private Timestamp landTime;

    private int takeoffAirportId;

    private int landAirportId;

    private int airlineId;

    private String status;

    public FLightDto(Flight flight) {
        this.flightId = flight.getId();
        this.takeoffTime = flight.getTakeoffTime();
        this.landTime = flight.getLandTime();
        this.takeoffAirportId = flight.getTakeoffAirport().getId();
        this.landAirportId = flight.getLandAirport().getId();
        this.airlineId = flight.getAirline().getId();
        this.status = flight.getStatus();
    }
}
