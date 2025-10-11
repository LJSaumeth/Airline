package edu.unimag.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class BookingDTOs {
    public record BookingCreateRequest(@NotNull Long passenger_id) implements Serializable {}

    public record BookingResponse(Long id, OffsetDateTime createdAt, String passenger_name, String passenger_email,
                                  List<BookingItemResponse> items) implements Serializable{}


    public record BookingItemCreateRequest(@NotBlank String cabin, @NotNull BigDecimal price, @NotNull Integer segmentOrder) implements Serializable {}

    public record BookingItemUpdateRequest(String cabin, BigDecimal price, Integer segmentOrder) implements Serializable {}

    public record BookingItemResponse(Long id, String cabin, BigDecimal price, Integer segmentOrder, Long booking_id, Long flight_id, String flight_number) implements Serializable{}
}