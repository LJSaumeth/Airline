package edu.unimag.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.SeatInventory;


public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {
	Optional<SeatInventory> findByFlight_IdAndCabin(Long flight_Id, Cabin cabin);
    List<SeatInventory> findByFlight_Id(Long flight_Id);
	boolean existsByFlight_IdAndCabinAndAvailableSeatsIsGreaterThanEqual(Long flight_Id, Cabin cabin, Integer min);
}
