package edu.unimag.services;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.unimag.api.dto.FlightDTOs.*;

public interface FlightService {
	FlightResponse createFlight(FlightCreateRequest request, Long airline_id, Long origin_airport_id, Long destination_airport_id);
    FlightResponse getFlight(Long id);
    FlightResponse updateFlight(FlightUpdateRequest request, Long id);
    void deleteFlight(Long id);
    
    Page<FlightResponse> listScheduledFlights(Long origin_airport_id, Long destination_airport_id, OffsetDateTime from, OffsetDateTime to, Pageable pageable);
    FlightResponse addTagToFlight(Long flight_id, Long tag_id);
    FlightResponse removeTagFromFlight(Long flight_id, Long tag_id);
    Page<FlightResponse> listFlightsByAirline(Long airline_id, Pageable pageable);
}
