package edu.unimag.api.dto;

import java.io.Serializable;
import java.util.List;
import jakarta.annotation.Nullable;

public class AirlineDTOs {
	public record AirlineCreateRequest(String code, String name) implements Serializable {}
    public record AirlineUpdateRequest(@Nullable String code, String name) implements Serializable {}
    public record AirlineResponse(Long id, String code, String name, List<FlightDTOs.FlightResponse> flights) implements Serializable {}

}
