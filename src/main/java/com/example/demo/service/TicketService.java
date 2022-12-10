package com.example.demo.service;

import com.example.demo.model.dto.ticket.CreateTicketRequestDto;
import com.example.demo.model.dto.ticket.TicketDto;
import com.example.demo.model.entity.Passenger;
import com.example.demo.model.entity.Ticket;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    FlightService flightService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    TicketRepository ticketRepository;

    public TicketDto createTicket(CreateTicketRequestDto createTicketRequestDto) {
        int passengerId = createTicketRequestDto.getPassengerId();
        if (passengerRepository.findById(passengerId).isEmpty()) {
            throw new IllegalArgumentException("Passenger with id: " + passengerId + " was not found!");
        }

        Passenger passenger = passengerRepository.findById(createTicketRequestDto.getPassengerId()).get();
        Ticket ticket = new Ticket();
        ticket.setPassenger(passenger);
        ticket.setFlight(flightService.findFlightById(createTicketRequestDto.getFlightId()));

        if (createTicketRequestDto.getAgencyId() != 0) {
            ticket.setTravelAgency(travelAgencyService.findAgencyById(createTicketRequestDto.getAgencyId()));
        }

        ticket.setAirline(airlineRepository.findById(createTicketRequestDto.getAirlineId()).get());
        ticket = ticketRepository.save(ticket);

        return new TicketDto(ticket);
    }

    public String deleteTicket(int ticketId) {
        if (ticketRepository.findById(ticketId).isEmpty()) {
            throw new IllegalArgumentException("Ticket with id: " + ticketId + " was not found!");
        }

        Ticket ticket = ticketRepository.findById(ticketId).get();
        ticketRepository.delete(ticket);

        return "Ticket with id: " + ticketId + " was deleted successfully!";
    }

}
