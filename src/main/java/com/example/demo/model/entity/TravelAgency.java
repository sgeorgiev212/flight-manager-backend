package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "travel_agencies")
public class TravelAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String status;

    @OneToMany(mappedBy = "travelAgency")
    @JsonManagedReference
    private List<BookingRequest> bookingRequests;

    @OneToMany(mappedBy = "travelAgency")
    @JsonManagedReference
    private List<Ticket> tickets;

}
