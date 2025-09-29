package edu.unimag.api.dto;

import java.io.Serializable;

import jakarta.annotation.Nonnull;

public class AirportDTOs {
	public record AirportCreateRequest(@Nonnull String code,@Nonnull String name, @Nonnull String city) implements Serializable {}
    public record AirportUpdateRequest(String code, @Nonnull String name) implements Serializable {}
    public record AirportResponse(Long id, String code, String name, String city ) implements Serializable {}
}
