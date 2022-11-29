package com.example.demo.model.dto.travelAgency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTravelAgencyRequestDto {

    private String name;

    private String address;

}
