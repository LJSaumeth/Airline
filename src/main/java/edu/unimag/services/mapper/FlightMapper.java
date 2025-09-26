package edu.unimag.services.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import edu.unimag.api.dto.FlightDTOs.FlightCreateRequest;
import edu.unimag.api.dto.FlightDTOs.FlightResponse;
import edu.unimag.api.dto.FlightDTOs.FlightUpdateRequest;
import edu.unimag.api.dto.TagDTOs.TagResponse;
import edu.unimag.domain.entity.Flight;
import edu.unimag.domain.entity.Tag;

public class FlightMapper {
    public static Flight ToEntity(FlightCreateRequest request) {
        return Flight.builder().number(request.number()).build();
    }
    public static FlightResponse toDTO(Flight flight) {
        Set<TagResponse> tagResponses = flight.getTags() == null ? Collections.emptySet() :
                flight.getTags().stream()
                        .map(tag -> new TagResponse(tag.getId(), tag.getName(), Collections.emptySet()))
                        .collect(Collectors.toSet());

        return  new FlightResponse(
                flight.getId(), flight.getNumber(), flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAirline() != null ? flight.getAirline().getId() : null,
                flight.getOrigin() != null ? flight.getOrigin().getId() : null,
                flight.getDestination() != null ? flight.getDestination().getId() : null,
                tagResponses
        );
    }

    public static void path(Flight entity, FlightUpdateRequest request ) {
        if (request.number() != null ) entity.setNumber(request.number());
        if (request.departureTime() != null ) entity.setDepartureTime(request.departureTime());
        if (request.arrivalTime() != null ) entity.setArrivalTime(request.arrivalTime());
        if (request.tagsNames() != null) request.tagsNames().forEach(tagName -> entity.addTag(Tag.builder().name(tagName).build()));

    }
}