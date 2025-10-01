package edu.unimag.services.mapper;

import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import edu.unimag.api.dto.PassengerDTOs.PassengerCreateRequest;
import edu.unimag.api.dto.PassengerDTOs.PassengerProfileDto;
import edu.unimag.api.dto.PassengerDTOs.PassengerResponse;
import edu.unimag.api.dto.PassengerDTOs.PassengerUpdateRequest;
import edu.unimag.domain.entity.Passenger;
import edu.unimag.domain.entity.PassengerProfile;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
	public interface PassengerMapper {

	    PassengerMapper INSTANCE = Mappers.getMapper(PassengerMapper.class);

	    // Crear pasajero con perfil embebido
	    Passenger toEntity(PassengerCreateRequest dto);

	    // Actualizar solo valores no nulos, incluido perfil
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    void updateEntityFromDto(PassengerUpdateRequest dto, @MappingTarget Passenger entity);

	    // Pasajero → DTO (perfil se mapea automáticamente si nombres coinciden)
	    PassengerResponse toDto(Passenger entity);

	    // Listado de pasajeros
	    List<PassengerResponse> toDtoList(List<Passenger> entities);
	}