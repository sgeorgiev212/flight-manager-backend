package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name="passenger_id")
    private Passenger passengerHasBooking;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="agency_id")
    private TravelAgency travelAgency;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="airline_id")
    private Airline airline;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="flight_id")
    private Flight flight;

    private String approver;

}
