package com.example.demo.controller;

import com.example.demo.model.dto.passenger.LoginRequestDto;
import com.example.demo.model.dto.passenger.LoginResponseDto;
import com.example.demo.model.dto.passenger.RegisterPassengerRequestDto;
import com.example.demo.model.dto.passenger.RegisterPassengerResponseDto;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @PostMapping
    public RegisterPassengerResponseDto registerUser(@RequestBody RegisterPassengerRequestDto registerPassengerDto)
    {
        return passengerService.registerPassenger(registerPassengerDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return passengerService.login(loginRequestDto);
    }

//    public void getAllBookings

}
