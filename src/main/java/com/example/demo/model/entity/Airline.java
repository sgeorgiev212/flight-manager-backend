package com.example.demo.model.entity;

import com.example.demo.model.dto.airline.CreateAirlineRequestDto;
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
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String status;

    public Airline(CreateAirlineRequestDto createAirlineRequestDto) {
        this.name = createAirlineRequestDto.getName();
        this.address = createAirlineRequestDto.getAddress();
    }
}
