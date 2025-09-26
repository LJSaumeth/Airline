package edu.unimag.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;


public class BookingDTOs {
    public record BookingCreateRequest(Long passenger_id, List<BookingItemDTOs.BookingItemCreateRequest> items) implements Serializable {}

    public record BookingUpdateRequest(List<BookingItemDTOs.BookingItemUpdateRequest> itemsToUpdate) implements Serializable {}

    public record BookingResponse(Long id, OffsetDateTime createdAt, PassengerDTOs.PassengerResponse passenger,
                                  List<BookingItemDTOs.BookingItemResponse> items) implements Serializable{}
}
