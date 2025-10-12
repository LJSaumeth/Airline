package edu.unimag.api.dto;

import java.io.Serializable;

import edu.unimag.domain.Enum.Cabin;
import jakarta.annotation.Nonnull;

public class SeatInventoryDTOs {
    public record SeatInventoryCreateRequest(@Nonnull Cabin cabin, @Nonnull Integer totalSeats, @Nonnull Integer availableSeats)
            implements Serializable {}
    public record SeatInventoryUpdateRequest(String cabin, Integer totalSeats, Integer availableSeats)
            implements Serializable {}
    public record SeatInventoryResponse(Long id, String cabin, Integer totalSeats, Integer availableSeats, Long flight_id)
            implements Serializable {}
}