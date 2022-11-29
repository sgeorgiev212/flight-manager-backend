package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking_requests")
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="passenger_id")
    private Passenger passengerHasBooking;

    @ManyToOne
    @JoinColumn(name="agency_id")
    private TravelAgency travelAgency;

    @ManyToOne
    @JoinColumn(name="airline_id")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name="flight_id")
    private Flight flight;

}
