package com.example.demo.model.dto.airport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditAirportDto implements AirportDto{

    private int id;

    private String name;

    private String country;
}
