package edu.unimag.services.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import edu.unimag.api.dto.FlightDTOs.FlightResponse;
import edu.unimag.api.dto.TagDTOs.TagCreateRequest;
import edu.unimag.api.dto.TagDTOs.TagResponse;
import edu.unimag.domain.entity.Tag;

public class TagMapper {
    public static Tag toEntity(TagCreateRequest request){
        return  Tag.builder().name(request.name()).build();
    }
    public static TagResponse toResponse(Tag tag){
        return new TagResponse(
                tag.getId(), tag.getName(),
                tag.getFlights() == null ? Set.of():tag.getFlights().stream().map(flight ->
                    new FlightResponse(
                            flight.getId(),flight.getNumber(), flight.getDepartureTime(),flight.getArrivalTime(),
                            flight.getAirline() != null ? flight.getAirline().getId() : null, flight.getOrigin() != null ? flight.getOrigin().getId() : null,
                            flight.getDestination() != null ? flight.getDestination().getId() : null, null
                    )).collect(Collectors.toSet())
        );
    }
    public static void path(Tag entity, TagCreateRequest request){
        if(entity.getName() != null) entity.setName(entity.getName());
    }
}