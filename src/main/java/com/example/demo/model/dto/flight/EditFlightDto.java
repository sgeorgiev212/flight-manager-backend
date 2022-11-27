package com.example.demo.model.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditFlightDto {

    private int flightId;

    private Timestamp takeoffTime;

    private Timestamp landTime;

    private int takeoffAirportId;

    private int landAirportId;

}
