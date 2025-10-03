package edu.unimag.services.mapper;

import edu.unimag.api.dto.AirportDTOs.AirportCreateRequest;
import edu.unimag.api.dto.AirportDTOs.AirportResponse;
import edu.unimag.api.dto.AirportDTOs.AirportUpdateRequest;
import edu.unimag.domain.entity.Airport;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    @Mapping(target = "id", ignore = true)
    Airport toEntity(AirportCreateRequest request);

    AirportResponse toResponse(Airport airport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    void patch(AirportUpdateRequest request, @MappingTarget Airport airport);
}