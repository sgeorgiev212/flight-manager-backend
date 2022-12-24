package com.example.demo.controller;

import com.example.demo.model.dto.airline.AirlineDto;
import com.example.demo.model.dto.airline.EditAirlineDto;
import com.example.demo.model.dto.airline.GetAllFlightsForAirlineByDateDto;
import com.example.demo.model.dto.flight.*;
import com.example.demo.model.dto.ticket.CreateTicketRequestDto;
import com.example.demo.model.dto.ticket.TicketDto;
import com.example.demo.model.entity.AirlineReview;
import com.example.demo.model.entity.BookingRequest;
import com.example.demo.service.AirlineService;
import com.example.demo.service.BookingRequestService;
import com.example.demo.service.FlightService;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airline")
@CrossOrigin(origins="http://localhost:8080")
public class AirlineController {

    @Autowired
    AirlineService airlineService;

    @Autowired
    FlightService flightService;

    @Autowired
    TicketService ticketService;

    @Autowired
    BookingRequestService bookingRequestService;

    @GetMapping("/{airlineId}/flights")
    public List<FLightDto> getAllAvailableFlightsForAirline(@PathVariable int airlineId) {
        return airlineService.getAllFlightsForAirline(airlineId);
    }

    @PutMapping
    public AirlineDto editAirline(@RequestBody EditAirlineDto editAirlineDto) {
        return airlineService.editAirline(editAirlineDto);
    }


    @PostMapping("flights")
    public List<FLightDto> getAllFLightsFromDate(@RequestBody GetAllFlightsForAirlineByDateDto getAllFlightsForAirlineByDateDto) {
        return airlineService.getAllFLightsForAirlineFromDate(getAllFlightsForAirlineByDateDto);
    }

    @PostMapping("/flight")
    public CreateFlightResponseDto createFLight(@RequestBody CreateFlightRequestDto createFlightRequestDto) {
        return flightService.createFlight(createFlightRequestDto);
    }

    @PutMapping("/flight")
    public FLightDto editFlight(@RequestBody EditFlightDto editFlightDto) {
        return flightService.editFlight(editFlightDto);
    }

    @PutMapping("/flights/{flightId}")
    public FLightDto cancelFlight(@PathVariable int flightId) {
        return flightService.cancelFlight(flightId);
    }

    @GetMapping
    public List<AirlineDto> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    @GetMapping("/{id}/bookings")
    public List<BookingRequestDto> getAllBookingRequestsForAirline(@PathVariable int id) {
        return airlineService.getAllBookingRequestsForAirline(id);
    }

    @PostMapping("/tickets")
    public TicketDto createTicket(@RequestBody CreateTicketRequestDto createTicketRequestDto) {
        return airlineService.createTicket(createTicketRequestDto);
    }

    @GetMapping("/{id}/reviews")
    public List<AirlineReview> getAllReviewsForAirline(@PathVariable int id) {
        return airlineService.getAllReviewsForAirline(id);
    }

    @DeleteMapping("/{id}/tickets/{ticketId}")
    public String deleteTicket(@PathVariable int id, @PathVariable int ticketId) {
        airlineService.findAirlineById(id);
        return ticketService.deleteTicket(ticketId);
    }

    @PostMapping("/{id}/bookings/{requestId}")
    public String createTicketFromBooking(@PathVariable int requestId) {
        BookingRequest bookingRequest = bookingRequestService.findBookingRequestById(requestId);
        TicketDto ticket = ticketService.createTicket(new CreateTicketRequestDto(bookingRequest));
        bookingRequestService.deleteBookingRequest(bookingRequest);
        return "Ticket with id : " + ticket.getId() + " was created from booking request " +
                "with id: " + bookingRequest.getId();
    }

}
