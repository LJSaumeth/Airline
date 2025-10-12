package edu.unimag.services.imp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.AirlineDTOs.*;
import edu.unimag.domain.entity.Airline;
import edu.unimag.domain.repositories.AirlineRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.AirlineService;
import edu.unimag.services.mapper.AirlineMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AirlineServiceImp implements AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper     airlineMapper;   // ← inject mapper

    @Override
    public AirlineResponse createAirline(AirlineCreateRequest request) {
        Airline airline = airlineMapper.toEntity(request);
        Airline saved   = airlineRepository.save(airline);
        return airlineMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirline(@Nonnull Long id) {
        return airlineRepository.findById(id)
            .map(airlineMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirlineByCode(@Nonnull String code) {
        return airlineRepository.findByCode(code)
            .map(airlineMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Airline with code %s not found.".formatted(code)));
    }

    @Override
    public AirlineResponse updateAirline(@Nonnull Long id, AirlineUpdateRequest request) {
        Airline airline = airlineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(id)));
        airlineMapper.patch(request, airline);
        Airline updated = airlineRepository.save(airline);
        return airlineMapper.toResponse(updated);
    }

    @Override
    public void deleteAirline(@Nonnull Long id) {
        airlineRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineResponse> listAllAirlines() {
        return airlineRepository.findAll().stream()
            .map(airlineMapper::toResponse)
            .toList();
    }

    @Override
    public Page<AirlineResponse> listAllAirlinesPage(Pageable pageable) {
        return airlineRepository.findAll(pageable)
            .map(airlineMapper::toResponse);
    }
}
