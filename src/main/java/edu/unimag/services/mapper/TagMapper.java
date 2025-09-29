package edu.unimag.services.mapper;

import edu.unimag.api.dto.TagDTOs.TagCreateRequest;
import edu.unimag.api.dto.TagDTOs.TagResponse;
import edu.unimag.domain.entity.Tag;

public class TagMapper {
	public static Tag toEntity(TagCreateRequest request){
        return  Tag.builder().name(request.name()).build();
    }
    public static TagResponse toResponse(Tag tag){
        return new TagResponse(tag.getId(), tag.getName());
    }
}