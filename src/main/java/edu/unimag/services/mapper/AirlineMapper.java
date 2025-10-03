package edu.unimag.services.mapper;

import edu.unimag.api.dto.AirlineDTOs.AirlineCreateRequest;
import edu.unimag.api.dto.AirlineDTOs.AirlineUpdateRequest;
import edu.unimag.api.dto.AirlineDTOs.AirlineResponse;
import edu.unimag.domain.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Airline toEntity(AirlineCreateRequest request);

    AirlineResponse toResponse(Airline airline);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    void patch(AirlineUpdateRequest request, @MappingTarget Airline entity);
}