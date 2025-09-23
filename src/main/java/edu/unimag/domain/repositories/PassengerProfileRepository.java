package edu.unimag.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimag.domain.entity.PassengerProfile;


public interface PassengerProfileRepository extends JpaRepository<PassengerProfile, Long> {
    List<PassengerProfile> findByCountryCode(String countryCode);
}
