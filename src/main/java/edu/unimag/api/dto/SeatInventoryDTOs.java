package edu.unimag.api.dto;

import java.io.Serializable;

import jakarta.annotation.Nullable;

public class SeatInventoryDTOs {
	public record SeatInventoryCreateRequest(String cabin, Integer totalSeats, Integer availableSeats, Long flight_id) implements Serializable {}
    public record SeatInventoryUpdateRequest(@Nullable String cabin, @Nullable Integer totalSeats,
                                             Integer availableSeats) implements Serializable {}
    public record SeatInventoryResponse(Long id, String cabin, Integer totalSeats, Integer availableSeats, Long flight_id) implements Serializable {}
}
