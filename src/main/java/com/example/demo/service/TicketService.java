package com.example.demo.service;

import com.example.demo.model.dto.CreateTicketRequestDto;
import com.example.demo.model.dto.TicketDto;
import com.example.demo.model.entity.Ticket;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    PassengerService passengerService;

    @Autowired
    FlightService flightService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    TicketRepository ticketRepository;

    public TicketDto createTicket(CreateTicketRequestDto createTicketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setPassenger(passengerService.findPassengerById(createTicketRequestDto.getPassengerId()));
        ticket.setFlight(flightService.findFlightById(createTicketRequestDto.getFlightId()));

        if (createTicketRequestDto.getAgencyId() != 0){
            ticket.setTravelAgency(travelAgencyService.findAgencyById(createTicketRequestDto.getAgencyId()));
        }

        ticket.setAirline(airlineRepository.findById(createTicketRequestDto.getAirlineId()).get());
        ticket = ticketRepository.save(ticket);

        return new TicketDto(ticket);
    }

}
