package com.example.demo.model.dto.flight;

import com.example.demo.model.entity.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    private int id;

    private int passengerId;

    private String agency;

    private String airline;

    private int flightId;

    private String approver;

    public BookingRequestDto(BookingRequest bookingRequest) {
        this.id = bookingRequest.getId();
        this.passengerId = bookingRequest.getPassengerHasBooking().getId();

        if (bookingRequest.getTravelAgency() != null) {
            this.agency = bookingRequest.getTravelAgency().getName();
        }

        this.airline = bookingRequest.getAirline().getName();
        this.flightId = bookingRequest.getFlight().getId();
        this.approver = bookingRequest.getApprover();
    }

}
