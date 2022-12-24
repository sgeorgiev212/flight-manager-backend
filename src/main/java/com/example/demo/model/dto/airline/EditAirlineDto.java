package com.example.demo.model.dto.airline;

import com.example.demo.model.entity.Airline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditAirlineDto {

    private int id;

    private String name;

    private String address;

    private String status;

    public EditAirlineDto(Airline airline) {
        this.id = airline.getId();
        this.name = airline.getName();
        this.address = airline.getAddress();
        this.status = airline.getStatus();
    }

}
