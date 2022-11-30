package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp takeoffTime;

    private Timestamp landTime;

    @OneToOne
    @JoinColumn(name = "takeoff_airport_id", referencedColumnName = "id")
    private Airport takeoffAirport;

    @OneToOne
    @JoinColumn(name = "land_airport_id", referencedColumnName = "id")
    private Airport landAirport;

    @OneToOne
    @JoinColumn(name = "airline_id", referencedColumnName = "id")
    private Airline airline;

    private String status;

    @OneToMany(mappedBy = "flight")
    private List<BookingRequest> bookingRequests;

    @OneToMany(mappedBy = "flight")
    private List<Ticket> tickets;

}
