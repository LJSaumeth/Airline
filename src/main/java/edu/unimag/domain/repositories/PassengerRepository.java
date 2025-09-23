package edu.unimag.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.unimag.domain.entity.Passenger;


public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByEmailIgnoreCase(String email);

    //Now with profile
    //Se me olvida despues, UPPER para que ingore las diferencias entre MAYUS y MAYUSN´T
    @Query("SELECT p FROM Passenger p JOIN FETCH p.profile WHERE LOWER(p.email) = LOWER(:email)")
    Optional<Passenger> findByEmailIgnoreCaseWithProfile(@Param("email") String email);
}

