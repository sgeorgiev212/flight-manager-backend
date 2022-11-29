package com.example.demo.repository;

import com.example.demo.model.entity.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelAgencyRepository extends JpaRepository<TravelAgency,Integer> {

    @Query(nativeQuery = true,
    value = "select * from travel_agencies where status = 'REGISTERED'")
    List<TravelAgency> findAllRegisteredAgencies();

    TravelAgency findByName(String name);

}
