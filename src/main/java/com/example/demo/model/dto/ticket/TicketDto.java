package com.example.demo.model.dto.ticket;

import com.example.demo.model.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private int id;

    private int passengerId;

    private int flightId;

    private int agencyId;

    private int airlineId;

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.passengerId = ticket.getPassenger().getId();
        this.flightId = ticket.getFlight().getId();
        this.agencyId = ticket.getTravelAgency().getId();
        this.airlineId = ticket.getAirline().getId();
    }
}
