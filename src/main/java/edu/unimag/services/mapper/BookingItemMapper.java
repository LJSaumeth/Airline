package edu.unimag.services.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import edu.unimag.api.dto.BookingDTOs.BookingItemCreateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingItemUpdateRequest;
import edu.unimag.api.dto.BookingDTOs.BookingItemResponse;
import edu.unimag.domain.entity.BookingItem;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingItemMapper {

    // Build a new BookingItem from create-DTO
    BookingItem toEntity(BookingItemCreateRequest dto);

    // Patch only non-null fields in update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(BookingItemUpdateRequest dto, @MappingTarget BookingItem entity);

    // Convert entity to response DTO
    BookingItemResponse toResponse(BookingItem entity);
}