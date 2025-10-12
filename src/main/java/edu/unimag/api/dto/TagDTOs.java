package edu.unimag.api.dto;

import java.io.Serializable;
import jakarta.annotation.Nonnull;

public class TagDTOs {
    public record TagCreateRequest(@Nonnull String name) implements Serializable {}
    public record TagResponse(Long id, String name) implements Serializable{}
}