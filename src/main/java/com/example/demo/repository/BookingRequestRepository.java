package com.example.demo.repository;

import com.example.demo.model.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRequestRepository extends JpaRepository<BookingRequest, Integer> {

    List<BookingRequest> findByAirlineId(int airlineId);

    @Query(nativeQuery = true,
    value = "select * from booking_requests where agency_id = ?1")
    List<BookingRequest> findAllByAgencyId(int agencyId);
}
