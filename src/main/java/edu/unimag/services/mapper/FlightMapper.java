package edu.unimag.services.mapper;

import edu.unimag.api.dto.FlightDTOs.FlightCreateRequest;
import edu.unimag.api.dto.FlightDTOs.FlightUpdateRequest;
import edu.unimag.api.dto.FlightDTOs.FlightResponse;
import edu.unimag.domain.entity.Airport;
import edu.unimag.domain.entity.Flight;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = { TagMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    /**
     * Crea un Flight minimal desde el DTO.
     * Asociaciones airline, origin, destination y tags se ignoran
     * para que puedas setearlas manualmente en el servicio.
     */
    @Mapping(target = "airline",     ignore = true)
    @Mapping(target = "origin",      ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "tags",        ignore = true)
    Flight toEntity(FlightCreateRequest dto);

    /**
     * Actualiza solo los campos no nulos de FlightUpdateRequest.
     * Mapea destination_airport_id → destination (stub Airport.id).
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "destination_airport_id", target = "destination", qualifiedByName = "toAirport")
    void updateEntityFromDto(FlightUpdateRequest dto, @MappingTarget Flight entity);

    /**
     * Convierte un Long en un Airport “stub” con solo el ID.
     * Adáptalo para inyectar un repositorio si necesitas cargar la entidad completa.
     */
    @Named("toAirport")
    default Airport toAirport(Long id) {
        if (id == null) {
            return null;
        }
        Airport a = new Airport();
        a.setId(id);
        return a;
    }

    /**
     * Mapea Flight → FlightResponse:
     *   - airline.id        → airline_id
     *   - origin.id         → origin_airport_id
     *   - destination.id    → destination_airport_id
     *   - tags              → tags (usando TagMapper.toDtoSet)
     */
    @Mapping(source = "airline.id",           target = "airline_id")
    @Mapping(source = "origin.id",            target = "origin_airport_id")
    @Mapping(source = "destination.id",       target = "destination_airport_id")
    @Mapping(source = "tags",                 target = "tags")
    FlightResponse toDto(Flight entity);

    List<FlightResponse> toDtoList(List<Flight> entities);
}