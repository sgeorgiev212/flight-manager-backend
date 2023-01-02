package com.example.demo.service;

import com.example.demo.model.dto.airline.AirlineDto;
import com.example.demo.model.dto.flight.BookingRequestDto;
import com.example.demo.model.dto.travelAgency.AddTravelAgencyReviewDto;
import com.example.demo.model.dto.travelAgency.EditTravelAgencyDto;
import com.example.demo.model.dto.travelAgency.RegisterTravelAgencyRequestDto;
import com.example.demo.model.dto.travelAgency.TravelAgencyDto;
import com.example.demo.model.entity.*;
import com.example.demo.repository.BookingRequestRepository;
import com.example.demo.repository.TravelAgencyRepository;
import com.example.demo.repository.TravelAgencyReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.util.ServiceUtil.REGISTERED_TRAVEL_AGENCY_STATUS;
import static com.example.demo.util.ServiceUtil.validateReviewText;

@Service
public class TravelAgencyService {

    @Autowired
    TravelAgencyRepository travelAgencyRepository;

    @Autowired
    TravelAgencyReviewRepository travelAgencyReviewRepository;

    @Autowired
    BookingRequestRepository bookingRequestRepository;

    @Autowired
    BookingRequestService bookingRequestService;

    public TravelAgencyDto registerTravelAgency(RegisterTravelAgencyRequestDto travelAgencyRequestDto) {
        String agencyName = travelAgencyRequestDto.getName();
        String agencyAddress = travelAgencyRequestDto.getAddress();

        if (agencyName == null || agencyName.length() < 3) {
            throw new IllegalArgumentException("Travel agency name length must be at least 3 characters!");
        }

        if (agencyAddress == null || agencyAddress.length() < 3) {
            throw new IllegalArgumentException("Travel agency address length must be at least 3 characters!");
        }

        if (travelAgencyRepository.findByName(agencyName) != null) {
            throw new IllegalArgumentException("Travel agency with name: " + agencyName + " already exists!");
        }

        TravelAgency travelAgency = new TravelAgency();
        travelAgency.setName(agencyName);
        travelAgency.setAddress(agencyAddress);
        travelAgency.setStatus(REGISTERED_TRAVEL_AGENCY_STATUS);
        travelAgency = travelAgencyRepository.save(travelAgency);

        return new TravelAgencyDto(travelAgency);
    }

    public TravelAgencyDto editTravelAgency(EditTravelAgencyDto editTravelAgencyRequestDto) {
        verifyEditAgencyDetails(editTravelAgencyRequestDto);
        TravelAgency agency = findAgencyById(editTravelAgencyRequestDto.getId());
        agency.setName(editTravelAgencyRequestDto.getName());
        agency.setAddress(editTravelAgencyRequestDto.getAddress());
        agency = travelAgencyRepository.save(agency);
        return new TravelAgencyDto(agency);
    }

    public List<TravelAgencyDto> getAllRegisteredAgencies() throws Exception {
        List<TravelAgency> registeredAgencies = travelAgencyRepository.findAllRegisteredAgencies();

        if (registeredAgencies == null || registeredAgencies.size() == 0) {
            throw new Exception("No registered travel agencies found!");
        }

        return registeredAgencies.stream()
                .map(agency -> new TravelAgencyDto(agency))
                .collect(Collectors.toList());
    }

    public TravelAgencyReview addReviewForTravelAgency(AddTravelAgencyReviewDto addTravelAgencyReviewDto, Passenger reviewer) {
        TravelAgency travelAgency = findAgencyById(addTravelAgencyReviewDto.getAgencyId());
        validateReviewText(addTravelAgencyReviewDto.getReview());

        TravelAgencyReview review = new TravelAgencyReview();
        review.setTravelAgency(travelAgency);
        review.setReviewer(reviewer);
        review.setReview(addTravelAgencyReviewDto.getReview());

        return travelAgencyReviewRepository.save(review);
    }

    public List<TravelAgencyReview> getAllReviewsForAgency(int agencyId) {
        TravelAgency agency = findAgencyById(agencyId);
        return agency.getReviews();
    }

    public List<BookingRequestDto> getAllBookingRequestsForTravelAgency(int agencyId) {
        findAgencyById(agencyId);

        return bookingRequestRepository.findAllByAgencyId(agencyId)
                .stream()
                .map(bookingRequest -> new BookingRequestDto(bookingRequest))
                .collect(Collectors.toList());
    }

    public String cancelBooking(int agencyId, int bookingId) {
        findAgencyById(agencyId);
        BookingRequest bookingRequest = bookingRequestService.findBookingRequestById(bookingId);
        return bookingRequestService.deleteBookingRequest(bookingRequest);
    }

    public void deleteReviewForAgency(int agencyId, int reviewId) {
        TravelAgency agency = findAgencyById(agencyId);

        List<TravelAgencyReview> reviews = agency.getReviews();
        Optional<TravelAgencyReview> reviewByID = reviews.stream()
                .filter(review -> review.getId() == reviewId)
                .findFirst();

        if (reviewByID.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " was not found!");
        }

        TravelAgencyReview review = reviewByID.get();
        reviews.remove(review);
        agency.setReviews(reviews);
        travelAgencyRepository.save(agency);
        travelAgencyReviewRepository.delete(review);
    }

    public TravelAgencyDto findAgencyByManagerId(int id) {
        TravelAgency agency = travelAgencyRepository.findByManagerId(id);
        if (agency == null) {
            throw new IllegalArgumentException("Travel agency with manager id " + id + " was not found!");
        }

        return new TravelAgencyDto(agency);
    }

    public TravelAgency findAgencyById(int agencyId) {
        Optional<TravelAgency> travelAgency = travelAgencyRepository.findById(agencyId);

        if (travelAgency.isEmpty()) {
            throw new IllegalArgumentException("Travel agency with id: " + agencyId + " was not found!");
        }

        return travelAgency.get();
    }

    private void verifyEditAgencyDetails(EditTravelAgencyDto editTravelAgencyDto) {
        if (editTravelAgencyDto.getAddress().length() < 5) {
            throw new IllegalArgumentException("Travel agency address must contain at least 5 characters!");
        }

        if (editTravelAgencyDto.getName().length() < 2) {
            throw new IllegalArgumentException("Travel agency name must contain at least 2 characters!");
        }
    }
}
