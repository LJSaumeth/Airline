package edu.unimag.services.mapper;

import edu.unimag.api.dto.AirlineDTOs.AirlineCreateRequest;
import edu.unimag.api.dto.AirlineDTOs.AirlineUpdateRequest;
import edu.unimag.api.dto.AirlineDTOs.AirlineResponse;
import edu.unimag.domain.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	public interface AirlineMapper {

	    // creation
	    Airline toEntity(AirlineCreateRequest dto);

	    // patch/update only non-null fields
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    void patch(AirlineUpdateRequest dto, @MappingTarget Airline entity);

	    // convert to response DTO
	    AirlineResponse toResponse(Airline entity);
	}
