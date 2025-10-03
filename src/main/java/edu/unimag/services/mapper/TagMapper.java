package edu.unimag.services.mapper;

import edu.unimag.api.dto.TagDTOs.TagCreateRequest;
import edu.unimag.api.dto.TagDTOs.TagResponse;
import edu.unimag.domain.entity.Tag;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Tag toEntity(TagCreateRequest request);

    TagResponse toResponse(Tag tag);
}