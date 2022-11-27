package com.example.demo.model.dto.airline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllFlightsForAirlineByDateDto {

    private int airlineId;

    private LocalDate takeoffDate;
}
