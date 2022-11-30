package com.example.demo.service;

import com.example.demo.model.dto.travelAgency.RegisterTravelAgencyRequestDto;
import com.example.demo.model.dto.travelAgency.TravelAgencyDto;
import com.example.demo.model.entity.TravelAgency;
import com.example.demo.repository.TravelAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.util.ServiceUtil.REGISTERED_TRAVEL_AGENCY_STATUS;

@Service
public class TravelAgencyService {

    @Autowired
    TravelAgencyRepository travelAgencyRepository;

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

    public List<TravelAgencyDto> getAllRegisteredAgencies() throws Exception {
        List<TravelAgency> registeredAgencies = travelAgencyRepository.findAllRegisteredAgencies();

        if (registeredAgencies == null || registeredAgencies.size() == 0) {
            throw new Exception("No registered travel agencies found!");
        }

        return registeredAgencies.stream()
                .map(agency -> new TravelAgencyDto(agency))
                .collect(Collectors.toList());
    }

    public TravelAgency findAgencyById(int agencyId) {
        Optional<TravelAgency> travelAgency = travelAgencyRepository.findById(agencyId);
        return (travelAgency.isPresent()) ? travelAgency.get() : null;
    }

}
