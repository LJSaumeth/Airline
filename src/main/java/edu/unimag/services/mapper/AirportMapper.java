package edu.unimag.services.mapper;

import edu.unimag.api.dto.AirportDTOs.AirportCreateRequest;
import edu.unimag.api.dto.AirportDTOs.AirportResponse;
import edu.unimag.api.dto.AirportDTOs.AirportUpdateRequest;
import edu.unimag.domain.entity.Airport;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	public interface AirportMapper {

	    // Create a new entity
	    Airport toEntity(AirportCreateRequest dto);

	    // Patch only non-null fields
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    void patch(AirportUpdateRequest dto, @MappingTarget Airport entity);

	    // Convert entity to response DTO
	    AirportResponse toResponse(Airport entity);
	}
