package edu.unimag.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimag.domain.entity.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByCode(String code);
}
