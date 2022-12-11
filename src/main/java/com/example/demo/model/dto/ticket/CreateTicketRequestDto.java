package com.example.demo.model.dto.ticket;

import com.example.demo.model.entity.BookingRequest;
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

    public CreateTicketRequestDto(BookingRequest bookingRequest) {
        this.passengerId = bookingRequest.getPassengerHasBooking().getId();
        this.flightId = bookingRequest.getFlight().getId();
        if (bookingRequest.getTravelAgency() != null) {
            this.agencyId = bookingRequest.getTravelAgency().getId();
        }
        this.airlineId = bookingRequest.getAirline().getId();
    }
}
