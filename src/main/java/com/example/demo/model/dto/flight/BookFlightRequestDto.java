package com.example.demo.model.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookFlightRequestDto {

    private int passengerId;

    private int agencyId;

    private int airlineId;

    private int flightId;

}
