package com.example.demo.model.entity;

import com.example.demo.model.dto.passenger.RegisterPassengerRequestDto;
import com.example.demo.util.PassengerUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String address;

    private String role;

    @OneToMany(mappedBy = "passengerHasBooking")
    private List<BookingRequest> bookingRequests;

    public Passenger(RegisterPassengerRequestDto registerPassengerDto) {
        this.firstName = registerPassengerDto.getFirstName();
        this.lastName = registerPassengerDto.getLastName();
        this.email = registerPassengerDto.getEmail();
        this.password = registerPassengerDto.getPassword();
        this.address = registerPassengerDto.getAddress();
        this.role = PassengerUtil.DEFAULT_PASSENGER_ROLE;
    }
}
