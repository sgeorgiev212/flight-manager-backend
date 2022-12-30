package com.example.demo.repository;

import com.example.demo.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    Optional<Passenger> findByEmail(String email);

    Passenger findByResetPasswordToken(String token);

}
