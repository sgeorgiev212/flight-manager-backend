package com.example.demo.model.dto.airport;

import com.example.demo.model.entity.Airport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAirportResponseDto {

    private int id;

    private String name;

    private String country;

    public CreateAirportResponseDto(Airport airport) {
        this.id = airport.getId();
        this.name = airport.getName();
        this.country = airport.getCountry();
    }
}
