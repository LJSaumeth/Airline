package edu.unimag.services.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import edu.unimag.api.dto.BookingDTOs.BookingItemUpdateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingItemCreateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingItemResponse;
import edu.unimag.api.dto.BookingDTOs.BookingResponse;
import edu.unimag.domain.Enum.Cabin;
import edu.unimag.domain.entity.Booking;
import edu.unimag.domain.entity.BookingItem;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {

BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

BookingItem toEntity(BookingItemCreateRequest dto);

@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
void updateEntityFromDto(BookingItemUpdateRequest dto, @MappingTarget BookingItem entity);

@Mapping(source = "booking.id", target = "booking_id")
@Mapping(source = "flight.id", target = "flight_id")
@Mapping(source = "flight.number", target = "flight_number")
BookingItemResponse toDto(BookingItem entity);

List<BookingItemResponse> toDtoList(List<BookingItem> entities);
}