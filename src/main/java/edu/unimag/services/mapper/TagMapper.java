package edu.unimag.services.mapper;

import edu.unimag.api.dto.TagDTOs.TagCreateRequest;
import edu.unimag.api.dto.TagDTOs.TagResponse;
import edu.unimag.domain.entity.Tag;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    /**
     * Crea un Tag mínimo desde el DTO.
     * Ignora id y relaciones (por ejemplo flights) si existen.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Tag toEntity(TagCreateRequest dto);

    /**
     * Mapea Tag → TagResponse (id y name).
     */
    TagResponse toDto(Tag tag);

    /**
     * Convierte un Set de Tag a un Set de TagResponse.
     */
    Set<TagResponse> toDtoSet(Set<Tag> tags);
}