package com.example.demo.model.dto.passenger;

import com.example.demo.model.entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPassengerResponseDto {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String pictureUrl;

    private String phoneNumber;

    private String age;

    public RegisterPassengerResponseDto(Passenger passenger) {

        this.id = passenger.getId();
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.email = passenger.getEmail();
        this.address = passenger.getAddress();
        this.pictureUrl = passenger.getPictureUrl();
        this.phoneNumber = passenger.getPhoneNumber();
        this.age = passenger.getAge();

    }
}
