package com.example.demo.service;

import com.example.demo.model.entity.BookingRequest;
import com.example.demo.repository.BookingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingRequestService {

    @Autowired
    BookingRequestRepository bookingRequestRepository;

    public String cancelABookingForPassenger(int passengerId, int bookingId) {
        BookingRequest bookingRequest = findBookingRequestById(bookingId);
        if (bookingRequest == null) {
            throw new IllegalArgumentException("Passenger with id: " + passengerId + " was not found!");
        }

        bookingRequestRepository.delete(bookingRequest);
        return "Booking with id: " + bookingId + " " +
                "for passenger with id :" + passengerId + " was deleted successfully!";
    }

    public BookingRequest findBookingRequestById(int bookingRequestId) {
        Optional<BookingRequest> bookingRequest = bookingRequestRepository.findById(bookingRequestId);
        return (bookingRequest.isEmpty()) ? null : bookingRequest.get();
    }

}
