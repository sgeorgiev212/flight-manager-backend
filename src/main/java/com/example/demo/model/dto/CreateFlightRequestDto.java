package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlightRequestDto {

    private Timestamp takeoffTime;

    private Timestamp landTime;

    private int takeoffAirportId;

    private int landAirportId;

    private int airlineId;

}
