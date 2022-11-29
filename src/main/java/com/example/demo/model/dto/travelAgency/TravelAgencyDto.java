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
public class TravelAgencyDto {

    private int id;

    private String name;

    private String address;

    private String status;

    public TravelAgencyDto(TravelAgency travelAgency) {
        this.id = travelAgency.getId();
        this.name = travelAgency.getName();
        this.address = travelAgency.getAddress();
        this.status = travelAgency.getStatus();
    }
}
