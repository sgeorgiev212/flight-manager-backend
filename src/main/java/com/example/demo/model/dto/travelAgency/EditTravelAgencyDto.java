package com.example.demo.model.dto.travelAgency;

import com.example.demo.model.entity.TravelAgency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditTravelAgencyDto {

    private int id;

    private String name;

    private String address;

    public EditTravelAgencyDto(TravelAgency travelAgency) {
        this.id = travelAgency.getId();
        this.name = travelAgency.getName();
        this.address = travelAgency.getAddress();
    }

}
