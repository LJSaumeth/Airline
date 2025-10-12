package edu.unimag.services;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.unimag.api.dto.FlightDTOs.*;

public interface FlightService {
    FlightResponse createFlight(FlightCreateRequest request);
    FlightResponse getFlight(Long id);
    FlightResponse updateFlight(Long id, FlightUpdateRequest request);
    void deleteFlight(Long id);
    Page<FlightResponse> listScheduledFlights(String origin_airport_code, String destination_airport_code,
                                              OffsetDateTime from, OffsetDateTime to, Pageable pageable);
    FlightResponse addTagToFlight(Long flight_id, Long tag_id);
    FlightResponse removeTagFromFlight(Long flight_id, Long tag_id);
    Page<FlightResponse> listFlightsByAirline(String airline_name, Pageable pageable);
}