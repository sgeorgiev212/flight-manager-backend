package com.example.demo.model.dto.passenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileDto {

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String pictureUrl;

    private String phoneNumber;

    private String age;

}
