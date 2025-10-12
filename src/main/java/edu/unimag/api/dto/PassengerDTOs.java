package edu.unimag.api.dto;

import java.io.Serializable;

import jakarta.annotation.Nonnull;

public class PassengerDTOs {
    public record PassengerCreateRequest(@Nonnull String fullName, @Nonnull String email,
                                         PassengerProfileDto profile) implements Serializable{}
    public record PassengerProfileDto(String phone, String countryCode) implements Serializable{}

    public record PassengerUpdateRequest(String fullName, String email, PassengerProfileDto profile) implements Serializable{}

    public record PassengerResponse(Long id, String fullName, String email, PassengerProfileDto profile) implements Serializable{}
}