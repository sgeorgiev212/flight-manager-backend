package com.example.demo.model.dto.passenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPassengerRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;

}
