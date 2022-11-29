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

    private int agencyId;

    private int airlineId;

    private int flightId;

    public BookingRequestDto(BookingRequest bookingRequest) {
        this.id = bookingRequest.getId();
        this.passengerId = bookingRequest.getPassengerHasBooking().getId();

        if (bookingRequest.getTravelAgency() != null) {
            this.agencyId = bookingRequest.getTravelAgency().getId();
        }

        this.airlineId = bookingRequest.getAirline().getId();
        this.flightId = bookingRequest.getFlight().getId();
    }

}
