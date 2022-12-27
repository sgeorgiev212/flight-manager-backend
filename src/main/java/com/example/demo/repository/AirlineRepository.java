package com.example.demo.repository;

import com.example.demo.model.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {

    @Query(nativeQuery = true,
            value = "select * from airlines where status = 'CREATED'")
    List<Airline> findAllCreatedAirlines();

    Airline findByName(String name);

    Airline findByManagerId(int managerId);

}
