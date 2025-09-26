package edu.unimag.api.dto;

import java.io.Serializable;

import jakarta.annotation.Nullable;

public class AirportDTOs {
	public record AirportCreateRequest(String code, String name, String city) implements Serializable {}
    public record AirportResponse(Long id, String code, String name, String city ) implements Serializable {}
    public record AirportUpdateRequest(@Nullable String code, String name) implements Serializable {}


}
