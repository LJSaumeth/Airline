package edu.unimag.api.dto;

import java.io.Serializable;
import jakarta.annotation.Nonnull;

public class AirlineDTOs {
	public record AirlineCreateRequest(@Nonnull String code,@Nonnull String name) implements Serializable {}
    public record AirlineUpdateRequest(String code, @Nonnull String name) implements Serializable {}
    public record AirlineResponse(Long id, String code, String name) implements Serializable {}
}
