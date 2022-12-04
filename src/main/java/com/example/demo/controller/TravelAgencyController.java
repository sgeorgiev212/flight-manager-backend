package com.example.demo.controller;

import com.example.demo.model.dto.travelAgency.RegisterTravelAgencyRequestDto;
import com.example.demo.model.dto.travelAgency.TravelAgencyDto;
import com.example.demo.model.entity.TravelAgencyReview;
import com.example.demo.service.TravelAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("agencies")
public class TravelAgencyController {

    @Autowired
    TravelAgencyService travelAgencyService;

    @PostMapping
    public TravelAgencyDto registerTravelAgency(@RequestBody RegisterTravelAgencyRequestDto travelAgencyRequestDto) {
        return travelAgencyService.registerTravelAgency(travelAgencyRequestDto);
    }

    @GetMapping
    public List<TravelAgencyDto> getAllRegisteredAgencies() throws Exception {
        return travelAgencyService.getAllRegisteredAgencies();
    }

    @GetMapping("/{id}/reviews")
    public List<TravelAgencyReview> getAllReviewsForAgency(@PathVariable int id) {
        return travelAgencyService.getAllReviewsForAgency(id);
    }

}
