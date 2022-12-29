package com.example.demo.model.dto.passenger;

import com.example.demo.model.entity.BookingRequest;
import com.example.demo.model.entity.Passenger;
import com.example.demo.model.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {

    int id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String role;

    private String pictureUrl;

    private String phoneNumber;

    private String age;

    private List<BookingRequest> bookingRequests;

    private List<Ticket> tickets;

    public PassengerDto(Passenger passenger) {
        this.id = passenger.getId();
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.email = passenger.getEmail();
        this.address = passenger.getAddress();
        this.role = passenger.getRole();
        this.pictureUrl = passenger.getPictureUrl();
        this.phoneNumber = passenger.getPhoneNumber();
        this.age = passenger.getAge();
        this.bookingRequests = passenger.getBookingRequests();
        this.tickets = passenger.getTickets();
    }
}
