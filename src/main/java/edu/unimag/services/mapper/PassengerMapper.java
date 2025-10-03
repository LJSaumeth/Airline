package edu.unimag.services.mapper;

import org.mapstruct.*;
import edu.unimag.api.dto.PassengerDTOs.PassengerCreateRequest;
import edu.unimag.api.dto.PassengerDTOs.PassengerProfileDto;
import edu.unimag.api.dto.PassengerDTOs.PassengerResponse;
import edu.unimag.api.dto.PassengerDTOs.PassengerUpdateRequest;
import edu.unimag.domain.entity.Passenger;
import edu.unimag.domain.entity.PassengerProfile;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "profile", target = "profile")
    Passenger toEntity(PassengerCreateRequest request);

    PassengerResponse toResponse(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void patch(PassengerUpdateRequest request, @MappingTarget Passenger entity);

    @AfterMapping
    default void updateProfile(PassengerUpdateRequest request, @MappingTarget Passenger entity) {
        if (request.profile() != null) {
            if (entity.getProfile() == null) {
                entity.setProfile(new PassengerProfile());
            }
            patchProfile(request.profile(), entity.getProfile());
        }
    }

    @Mapping(target = "id", ignore = true)
    PassengerProfile toProfileEntity(PassengerProfileDto profileDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void patchProfile(PassengerProfileDto profileDto, @MappingTarget PassengerProfile profile);
}