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
@Table(name = "travel_agency_reviews")
public class TravelAgencyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private Passenger reviewer;

    @ManyToOne
    @JoinColumn(name="agency_id")
    @JsonBackReference
    private TravelAgency travelAgency;

    private String review;

}
