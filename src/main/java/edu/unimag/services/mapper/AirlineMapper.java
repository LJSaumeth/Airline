package edu.unimag.services.mapper;

import edu.unimag.api.dto.AirlineDTOs.AirlineCreateRequest;
import edu.unimag.api.dto.AirlineDTOs.AirlineResponse;
import edu.unimag.api.dto.AirlineDTOs.AirlineUpdateRequest;
import edu.unimag.domain.entity.Airline;

public class AirlineMapper {
	 public static Airline toEntity(AirlineCreateRequest request){
	        return Airline.builder().code(request.code()).name(request.name()).build();
	    }

	    public static AirlineResponse toResponse(Airline entity){
	        return new AirlineResponse(entity.getId(), entity.getCode(), entity.getName());
	    }

	    public static void patch(Airline entity, AirlineUpdateRequest request){
	        if (request.code() != null) entity.setCode(request.code());
	        if (request.name() != null) entity.setName(request.name());
	    }
}