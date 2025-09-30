package edu.unimag.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

import edu.unimag.api.dto.TagDTOs.TagResponse;
import jakarta.annotation.Nonnull;

public class FlightDTOs {
	 public record FlightCreateRequest(@Nonnull String number, @Nonnull OffsetDateTime departureTime, @Nonnull OffsetDateTime arrivalTime) implements Serializable {}

	    public record FlightUpdateRequest(String number, OffsetDateTime departureTime, OffsetDateTime arrivalTime, Long destination_airport_id)
	            implements Serializable {}

	    public record FlightResponse(Long id, String number, OffsetDateTime departureTime, OffsetDateTime arrivalTime,
	                                 Long airline_id, Long origin_airport_id, Long destination_airport_id,
	                                 Set<TagResponse> tags) implements Serializable {}

}
