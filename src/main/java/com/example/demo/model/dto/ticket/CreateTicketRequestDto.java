package com.example.demo.model.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequestDto {

    private int passengerId;

    private int flightId;

    private int agencyId;

    private int airlineId;

}
