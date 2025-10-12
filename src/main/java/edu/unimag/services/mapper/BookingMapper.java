package edu.unimag.services.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import edu.unimag.api.dto.BookingDTOs.BookingItemUpdateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingItemCreateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingItemResponse;
import edu.unimag.api.dto.BookingDTOs.BookingResponse;
import edu.unimag.domain.entity.Booking;
import edu.unimag.domain.entity.BookingItem;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    // Booking
    @Mapping(source = "passenger.fullName", target = "passenger_name")
    @Mapping(source = "passenger.email", target = "passenger_email")
    @Mapping(source = "items", target = "items")
    BookingResponse toResponse(Booking booking);

    //Booking Item
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", ignore = true)
    BookingItem toItemEntity(BookingItemCreateRequest request);

    @Mapping(source = "booking.id", target = "booking_id")
    @Mapping(source = "flight.id", target = "flight_id")
    @Mapping(source = "flight.number", target = "flight_number")
    BookingItemResponse toItemResponse(BookingItem item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", ignore = true)
    void patch(BookingItemUpdateRequest request, @MappingTarget BookingItem item);

}