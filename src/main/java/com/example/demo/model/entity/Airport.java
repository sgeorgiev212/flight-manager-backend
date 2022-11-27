package com.example.demo.model.entity;

import com.example.demo.model.dto.airport.CreateAirportDto;
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
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String country;

    public Airport(CreateAirportDto createAirportDto) {
        this.name = createAirportDto.getName();
        this.country = createAirportDto.getCountry();
    }
}
