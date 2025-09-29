package edu.unimag.services.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.SeatInventoryDTOs.*;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.repositories.*;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.SeatInventoryService;
import edu.unimag.services.mapper.SeatInventoryMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class SeatInventoryServiceImp implements SeatInventoryService{
	private final SeatInventoryRepository seatInventoryRepository;
    private final FlightRepository flightRepository;

    @Override @Transactional
    public SeatInventoryResponse createSeatInventory(@Nonnull Long flight_id, SeatInventoryCreateRequest request) {
        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found.".formatted(flight_id))
        );
        var entitySeat = SeatInventoryMapper.toEntity(request);
        entitySeat.setFlight(flight);
        return SeatInventoryMapper.toResponse(seatInventoryRepository.save(entitySeat));
    }

    @Override
    public SeatInventoryResponse getSeatInventory(@Nonnull Long id) {
        return seatInventoryRepository.findById(id).map(SeatInventoryMapper::toResponse).orElseThrow(
                () -> new NotFoundException("SeatInventory %d not found.".formatted(id))
        );
    }

    @Override @Transactional
    public SeatInventoryResponse updateSeatInventory(@Nonnull Long id, SeatInventoryUpdateRequest request) {
        var seatInventory = seatInventoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("SeatInventory %d not found.".formatted(id))
        );
        SeatInventoryMapper.patch(seatInventory, request);
        return SeatInventoryMapper.toResponse(seatInventoryRepository.save(seatInventory));
    }

    @Override @Transactional
    public void deleteSeatInventory(@Nonnull Long id) {
        seatInventoryRepository.deleteById(id);
    }

    @Override
    public List<SeatInventoryResponse> listSeatInventoriesByFlight(@Nonnull Long flight_id) {
        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found.".formatted(flight_id))
        );
        return seatInventoryRepository.findByFlight_Id(flight.getId()).stream().map(SeatInventoryMapper::toResponse).toList();
    }

    @Override
    public SeatInventoryResponse getSeatInventoryByFlightAndCabin(@Nonnull Long flight_id, @Nonnull String cabin) {
        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found.".formatted(flight_id))
        );
        var seat = seatInventoryRepository.findByFlight_IdAndCabin(flight.getId(), Cabin.valueOf(cabin)).orElseThrow(
                () -> new NotFoundException("SeatInventory for flight %d with Cabin %s not found.".formatted(flight_id, cabin))
        );
        return SeatInventoryMapper.toResponse(seat);
    }

    @Override
    public boolean existsSeatInventoryByFlightAndCabinWithMinAvailableSeats(@Nonnull Long flight_id, @Nonnull String cabin, @Nonnull Integer min) {
        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found.".formatted(flight_id))
        );
        return seatInventoryRepository.existsByFlight_IdAndCabinAndAvailableSeatsIsGreaterThanEqual(flight.getId(), Cabin.valueOf(cabin), min);
    }
}
