package com.example.demo.repository;

import com.example.demo.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {

    @Query(nativeQuery = true,
    value = "select * from flights where takeoff_time = ?1 and land_time = ?2 and takeoff_airport_id = ?3 and land_airport_id = ?4 and airline_id = ?5 ")
    Flight checkIfFLightIsAlreadyRegistered(Timestamp takeoffTime, Timestamp landTime, int takeoffAirportId, int landAirportId, int airlineId);

    List<Flight> findAllByAirlineId(int airlineId);

    @Query(nativeQuery = true,
    value = "select * from flights where airline_id = ?1 and takeoff_time between ?2 and ?3")
    List<Flight> findAllByAirlineIdAndTakeoffTime(int airlineId, Timestamp takeoffDateStart, Timestamp takeoffDateEnd);

}
