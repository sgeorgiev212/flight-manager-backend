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
public class LoginResponseDto {

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    public LoginResponseDto(Passenger passenger) {
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.email = passenger.getEmail();
        this.address = passenger.getAddress();
    }
}
