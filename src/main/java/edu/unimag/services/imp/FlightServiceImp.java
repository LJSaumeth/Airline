package edu.unimag.services.imp;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.FlightDTOs.*;
import edu.unimag.domain.entity.Airport;
import edu.unimag.domain.entity.Flight;
import edu.unimag.domain.entity.Tag;
import edu.unimag.domain.repositories.*;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.FlightService;
import edu.unimag.services.mapper.FlightMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImp implements FlightService {
    private final FlightRepository   flightRepository;
    private final AirlineRepository  airlineRepository;
    private final AirportRepository  airportRepository;
    private final TagRepository      tagRepository;
    private final FlightMapper       flightMapper;

    @Override
    public FlightResponse createFlight(
        FlightCreateRequest request,
        @Nonnull Long airlineId,
        @Nonnull Long originAirportId,
        @Nonnull Long destinationAirportId
    ) {
        var airline = airlineRepository.findById(airlineId)
            .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(airlineId)));
        var origin = airportRepository.findById(originAirportId)
            .orElseThrow(() -> new NotFoundException("Airport %d not found.".formatted(originAirportId)));
        var dest = airportRepository.findById(destinationAirportId)
            .orElseThrow(() -> new NotFoundException("Airport %d not found.".formatted(destinationAirportId)));

        // Map DTO → entity and wire in relationships
        Flight flight = flightMapper.toEntity(request);
        flight.setAirline(airline);
        flight.setOrigin(origin);
        flight.setDestination(dest);

        // Persist then map to DTO
        Flight saved = flightRepository.save(flight);
        return flightMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightResponse getFlight(@Nonnull Long id) {
        return flightRepository.findById(id)
            .map(flightMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Flight %d not found.".formatted(id)));
    }

    @Override
    public FlightResponse updateFlight(
        FlightUpdateRequest request,
        @Nonnull Long id
    ) {
        Flight flight = flightRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Flight %d not found.".formatted(id)));

        // Null-safe patch of updatable fields
        flightMapper.patch(request, flight);



        Flight updated = flightRepository.save(flight);
        return flightMapper.toResponse(updated);
    }

    @Override
    public void deleteFlight(@Nonnull Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlightResponse> listScheduledFlights(
        @Nonnull Long originAirportId,
        @Nonnull Long destinationAirportId,
        OffsetDateTime from,
        OffsetDateTime to,
        Pageable pageable
    ) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("\"From\" date is after \"to\" date");
        }

        // Look up optional airports
        var optOrigin = airportRepository.findById(originAirportId);
        var optDest   = airportRepository.findById(destinationAirportId);

        Page<Flight> flights = optOrigin.isPresent() && optDest.isPresent()
            ? flightRepository.findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(
                  optOrigin.get().getCode(),
                  optDest.get().getCode(),
                  from,
                  to,
                  pageable
              )
            : new PageImpl<>(
                  flightRepository.filterByOriginAndDestinationOptionalAndDepartureTimeBetween(
                      optOrigin.map(Airport::getCode).orElse(null),
                      optDest.map(Airport::getCode).orElse(null),
                      from,
                      to
                  ),
                  pageable,
                  0
              );

        return flights.map(flightMapper::toResponse);
    }

    @Override
    public FlightResponse addTagToFlight(@Nonnull Long flightId, @Nonnull Long tagId) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flightId)));
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(tagId)));

        // Maintain both sides of the ManyToMany
        flight.getTags().add(tag);
        tag.getFlights().add(flight);

        Flight saved = flightRepository.save(flight);
        return flightMapper.toResponse(saved);
    }

    @Override
    public FlightResponse removeTagFromFlight(@Nonnull Long flightId, @Nonnull Long tagId) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flightId)));
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(tagId)));

        flight.getTags().remove(tag);
        tag.getFlights().remove(flight);

        Flight saved = flightRepository.save(flight);
        return flightMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlightResponse> listFlightsByAirline(
        @Nonnull Long airlineId,
        Pageable pageable
    ) {
        var airline = airlineRepository.findById(airlineId)
            .orElseThrow(() -> new NotFoundException("Airline %d not found".formatted(airlineId)));

        return flightRepository.findByAirline_Name(airline.getName(), pageable)
            .map(flightMapper::toResponse);
    }
}
