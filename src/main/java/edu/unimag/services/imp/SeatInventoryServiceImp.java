package edu.unimag.services.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.SeatInventoryDTOs.*;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.Flight;
import edu.unimag.domain.entity.SeatInventory;
import edu.unimag.domain.repositories.*;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.SeatInventoryService;
import edu.unimag.services.mapper.SeatInventoryMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatInventoryServiceImp implements SeatInventoryService {
    private final SeatInventoryRepository seatInventoryRepository;
    private final FlightRepository        flightRepository;
    private final SeatInventoryMapper     seatInventoryMapper;

    @Override
    @Transactional
    public SeatInventoryResponse createSeatInventory(
        @Nonnull Long flightId,
        SeatInventoryCreateRequest request
    ) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException(
                "Flight %d not found.".formatted(flightId)
            ));

        SeatInventory entity = seatInventoryMapper.toEntity(request);
        entity.setFlight(flight);

        SeatInventory saved = seatInventoryRepository.save(entity);
        return seatInventoryMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SeatInventoryResponse getSeatInventory(@Nonnull Long id) {
        return seatInventoryRepository.findById(id)
            .map(seatInventoryMapper::toResponse)
            .orElseThrow(() -> new NotFoundException(
                "SeatInventory %d not found.".formatted(id)
            ));
    }

    @Override
    @Transactional
    public SeatInventoryResponse updateSeatInventory(
        @Nonnull Long id,
        SeatInventoryUpdateRequest request
    ) {
        SeatInventory entity = seatInventoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                "SeatInventory %d not found.".formatted(id)
            ));

        seatInventoryMapper.patch(request, entity);
        SeatInventory updated = seatInventoryRepository.save(entity);

        return seatInventoryMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteSeatInventory(@Nonnull Long id) {
        seatInventoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatInventoryResponse> listSeatInventoriesByFlight(@Nonnull Long flightId) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException(
                "Flight %d not found.".formatted(flightId)
            ));

        return seatInventoryRepository.findByFlight_Id(flight.getId()).stream()
            .map(seatInventoryMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SeatInventoryResponse getSeatInventoryByFlightAndCabin(
        @Nonnull Long flightId,
        @Nonnull String cabin
    ) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException(
                "Flight %d not found.".formatted(flightId)
            ));

        Cabin cabinEnum = Cabin.valueOf(cabin);
        SeatInventory seat = seatInventoryRepository
            .findByFlight_IdAndCabin(flight.getId(), cabinEnum)
            .orElseThrow(() -> new NotFoundException(
                "SeatInventory for flight %d with Cabin %s not found."
                .formatted(flightId, cabin)
            ));

        return seatInventoryMapper.toResponse(seat);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsSeatInventoryByFlightAndCabinWithMinAvailableSeats(
        @Nonnull Long flightId,
        @Nonnull String cabin,
        @Nonnull Integer min
    ) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException(
                "Flight %d not found.".formatted(flightId)
            ));

        return seatInventoryRepository
            .existsByFlight_IdAndCabinAndAvailableSeatsIsGreaterThanEqual(
                flight.getId(),
                Cabin.valueOf(cabin),
                min
            );
    }
}