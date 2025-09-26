package edu.unimag.services.mapper;

import java.time.OffsetDateTime;
import java.util.List;

import edu.unimag.api.dto.BookingDTOs.BookingCreateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingResponse;
import edu.unimag.api.dto.BookingDTOs.BookingUpdateRequest;
import edu.unimag.api.dto.BookingItemDTOs.BookingItemCreateRequest;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.Booking;
import edu.unimag.domain.entity.BookingItem;
import edu.unimag.domain.entity.Passenger;

public class BookingMapper {
    	public static Booking toEntity(BookingCreateRequest request, Passenger passenger, List<BookingItemCreateRequest> items) {
        var b = Booking.builder().createdAt(OffsetDateTime.now())
                .passenger(passenger).build();

        List<BookingItem> itemsToEntities = items.stream().map(item -> BookingItem.builder().cabin(Cabin.valueOf(item.cabin())).price(item.price())
                .segmentOrder(item.segmentOrder()).booking(b).build()).toList();
        itemsToEntities.forEach(b::addItem);
        return b;
    }
    public static BookingResponse toDTO(Booking entity) {
        return new BookingResponse(entity.getId(),
        	    entity.getCreatedAt(),
        	    PassengerMapper.toDTO(entity.getPassenger()), // instead of just getId()
        	    entity.getItems().stream().map(BookingItemMapper::toDTO).toList());
    }
    public static void patch(Booking entity, BookingUpdateRequest request, Passenger passenger, List<BookingItem> items) {

    }
}
