package edu.unimag.services.imp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.PassengerDTOs.*;
import edu.unimag.domain.entity.Passenger;
import edu.unimag.domain.repositories.PassengerRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.PassengerService;
import edu.unimag.services.mapper.PassengerMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PassengerServiceImp implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper     passengerMapper;

    @Override
    @Transactional
    public PassengerResponse createPassenger(PassengerCreateRequest request) {
        Passenger entity = passengerMapper.toEntity(request);
        Passenger saved  = passengerRepository.save(entity);
        return passengerMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassenger(@Nonnull Long id) {
        return passengerRepository.findById(id)
            .map(passengerMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Passenger %d not found.".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerByEmail(@Nonnull String email) {
        return passengerRepository.findByEmailIgnoreCase(email)
            .map(passengerMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Passenger with email %s not found.".formatted(email)));
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerWithProfile(@Nonnull String email) {
        return passengerRepository.findByEmailIgnoreCaseWithProfile(email)
            .map(passengerMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Passenger with email %s not found.".formatted(email)));
    }

    @Override
    @Transactional
    public PassengerResponse updatePassenger(
        @Nonnull Long id,
        PassengerUpdateRequest request
    ) {
        Passenger existing = passengerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Passenger %d not found.".formatted(id)));

        passengerMapper.patch(request, existing);
        Passenger updated = passengerRepository.save(existing);
        return passengerMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deletePassenger(@Nonnull Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassengerResponse> listAllPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable)
            .map(passengerMapper::toResponse);
    }
}
