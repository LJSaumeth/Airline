package edu.unimag.services.imp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.AirportDTOs.*;
import edu.unimag.domain.entity.Airport;
import edu.unimag.domain.repositories.AirportRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.AirportService;
import edu.unimag.services.mapper.AirportMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AirportServiceImp implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper     airportMapper;   // ← inject mapper

    @Override
    public AirportResponse createAirport(AirportCreateRequest request) {
        Airport airport = airportMapper.toEntity(request);
        Airport saved   = airportRepository.save(airport);
        return airportMapper.toResponse(saved);
    }

    @Override
    public AirportResponse getAirport(@Nonnull Long id) {
        return airportRepository.findById(id)
            .map(airportMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Airport %d not found.".formatted(id)));
    }

    @Override
    public AirportResponse getAirportByCode(@Nonnull String code) {
        return airportRepository.findByCode(code)
            .map(airportMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Airport with code %s not found.".formatted(code)));
    }

    @Override
    public List<AirportResponse> getCityAirports(@Nonnull String city) {
        return airportRepository.findByCity(city).stream()
            .map(airportMapper::toResponse)
            .toList();
    }

    @Override
    public AirportResponse updateAirport(@Nonnull Long id, AirportUpdateRequest request) {
        Airport airport = airportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Airport %d not found.".formatted(id)));
        airportMapper.patch(request, airport);
        Airport updated = airportRepository.save(airport);
        return airportMapper.toResponse(updated);
    }

    @Override
    public void deleteAirport(@Nonnull Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    public Page<AirportResponse> listAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable)
            .map(airportMapper::toResponse);
    }
}
