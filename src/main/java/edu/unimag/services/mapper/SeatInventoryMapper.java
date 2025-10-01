package edu.unimag.services.mapper;

import edu.unimag.api.dto.SeatInventoryDTOs.SeatInventoryCreateRequest;
import edu.unimag.api.dto.SeatInventoryDTOs.SeatInventoryUpdateRequest;
import edu.unimag.api.dto.SeatInventoryDTOs.SeatInventoryResponse;
import edu.unimag.domain.entity.SeatInventory;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SeatInventoryMapper {

    SeatInventoryMapper INSTANCE = Mappers.getMapper(SeatInventoryMapper.class);

    /**
     * Crea un SeatInventory “stub” desde el DTO.
     * El vuelo (flight) se ignora para que puedas setearlo manualmente.
     */
    @Mapping(target = "flight", ignore = true)
    SeatInventory toEntity(SeatInventoryCreateRequest dto);

    /**
     * Actualiza solo las propiedades no nulas.
     * El vuelo (flight) sigue ignorado en la actualización.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "flight", ignore = true)
    void updateEntityFromDto(SeatInventoryUpdateRequest dto, @MappingTarget SeatInventory entity);

    /**
     * Mapea SeatInventory → SeatInventoryResponse, extrayendo flight.id → flight_id.
     */
    @Mapping(source = "flight.id", target = "flight_id")
    SeatInventoryResponse toDto(SeatInventory entity);

    /**
     * Convierte una lista de entidades a lista de DTOs.
     */
    List<SeatInventoryResponse> toDtoList(List<SeatInventory> entities);
}