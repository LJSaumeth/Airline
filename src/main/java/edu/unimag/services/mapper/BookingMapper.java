package edu.unimag.services.mapper;

import java.util.List;

import edu.unimag.api.dto.BookingDTOs.*;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.*;

public class BookingMapper {
    public static BookingResponse toResponse(Booking entity) {
        var items = entity.getItems() == null? List.<BookingItemResponse>of() : entity.getItems().stream().map(BookingMapper::toItemResponse).toList();
        var passengerName = entity.getPassenger() == null? null: entity.getPassenger().getFullName();
        var passengerEmail = entity.getPassenger() == null? null: entity.getPassenger().getEmail();

        return new BookingResponse(entity.getId(), entity.getCreatedAt(), passengerName, passengerEmail, items);
    }

    public static BookingItemResponse toItemResponse(BookingItem entity) {
        return new BookingItemResponse(entity.getId(), entity.getCabin().name(), entity.getPrice(), entity.getSegmentOrder(), entity.getBooking().getId(),
                entity.getFlight().getId(), entity.getFlight().getNumber());
    }

    public static void itemPatch(BookingItem entity, BookingItemUpdateRequest request) {
        if (request.cabin() != null) entity.setCabin(Cabin.valueOf(request.cabin().toUpperCase()));
        if (request.price() != null) entity.setPrice(request.price());
        if (request.segmentOrder() != null) entity.setSegmentOrder(request.segmentOrder());
    }

    public static void addItem(BookingItem item, Booking booking){ booking.addItem(item); }
}
