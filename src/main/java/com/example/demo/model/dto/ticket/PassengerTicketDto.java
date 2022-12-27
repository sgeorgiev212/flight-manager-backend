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
public class PassengerTicketDto {

    private int id;

    private int passengerId;

    private int flightId;

    private String agency;

    private String airline;

    public PassengerTicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.passengerId = ticket.getPassenger().getId();
        this.flightId = ticket.getFlight().getId();

        if (ticket.getTravelAgency() != null) {
            this.agency = ticket.getTravelAgency().getName();
        }

        this.airline = ticket.getAirline().getName();
    }

}
