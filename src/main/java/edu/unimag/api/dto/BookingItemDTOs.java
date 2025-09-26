package edu.unimag.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.annotation.Nullable;

public class BookingItemDTOs {
	public record BookingItemCreateRequest(String cabin, BigDecimal price, Integer segmentOrder, Long flight_id) implements Serializable {}
    public record BookingItemUpdateRequest(String cabin, BigDecimal price, @Nullable Long flight_id) implements Serializable {}
    public record BookingItemResponse(Long id, String cabin, BigDecimal price, Integer segmentOrder,
                                      FlightDTOs.FlightResponse flight) implements Serializable{}
}
