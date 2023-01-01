package com.example.demo.controller;

import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.travelAgency.EditTravelAgencyDto;
import com.example.demo.model.dto.travelAgency.RegisterTravelAgencyRequestDto;
import com.example.demo.model.dto.travelAgency.TravelAgencyDto;
import com.example.demo.model.entity.TravelAgencyReview;
import com.example.demo.service.TravelAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agencies")
@CrossOrigin(origins = "http://localhost:8080")
public class TravelAgencyController {

    @Autowired
    TravelAgencyService travelAgencyService;

    @PostMapping
    public TravelAgencyDto registerTravelAgency(@RequestBody RegisterTravelAgencyRequestDto travelAgencyRequestDto) {
        return travelAgencyService.registerTravelAgency(travelAgencyRequestDto);
    }

    @PutMapping
    public TravelAgencyDto editTravelAgency(@RequestBody EditTravelAgencyDto editTravelAgencyDto) {
        return travelAgencyService.editTravelAgency(editTravelAgencyDto);
    }

    @GetMapping
    public List<TravelAgencyDto> getAllRegisteredAgencies() throws Exception {
        return travelAgencyService.getAllRegisteredAgencies();
    }

    @GetMapping("/{id}/reviews")
    public List<TravelAgencyReview> getAllReviewsForAgency(@PathVariable int id) {
        return travelAgencyService.getAllReviewsForAgency(id);
    }

    @GetMapping("/{id}/bookings")
    public List<BookingRequestDto> getAllBookingRequestsForTravelAgency(@PathVariable int id) {
        return travelAgencyService.getAllBookingRequestsForTravelAgency(id);
    }

    @DeleteMapping("/{id}/bookings/{bookingId}")
    public String cancelBooking(@PathVariable int id, @PathVariable int bookingId) {
        return travelAgencyService.cancelBooking(id, bookingId);
    }

    @DeleteMapping("/{id}/review/{reviewId}")
    public void deleteReviewForAgency(@PathVariable int id, @PathVariable int reviewId) {
        travelAgencyService.deleteReviewForAgency(id, reviewId);
    }

}
