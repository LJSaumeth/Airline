package edu.unimag.services.mapper;

import edu.unimag.api.dto.BookingItemDTOs.BookingItemCreateRequest;
import edu.unimag.api.dto.BookingItemDTOs.BookingItemResponse;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.BookingItem;
import edu.unimag.domain.entity.Flight;

public class BookingItemMapper {
    public static BookingItem toEntity(BookingItemCreateRequest request, Flight flight) {
        return BookingItem.builder().cabin(Cabin.valueOf(request.cabin().toUpperCase())).price(request.price())
            .flight(flight).build();
    }

    public static BookingItemResponse toDTO(BookingItem entity) {
        var fR = FlightMapper.toDTO(entity.getFlight());
        return new BookingItemResponse(entity.getId(), entity.getCabin().name(), entity.getPrice(), entity.getSegmentOrder(), fR);
    }

    public static void path(BookingItem entity, BookingItemCreateRequest request) {
        if (request.cabin() != null) {
            entity.setCabin(Cabin.valueOf(request.cabin().toUpperCase()));
        }
        if (request.price() != null) {
            entity.setPrice(request.price());
        }
        if (request.flight_id() != null) {
            entity.setFlight(Flight.builder().id(request.flight_id()).build());
        }
    }
}