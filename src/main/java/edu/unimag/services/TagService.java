package edu.unimag.services;

import java.util.Collection;
import java.util.List;

import edu.unimag.api.dto.TagDTOs.*;

public interface TagService {
    TagResponse createTag(TagCreateRequest request);
    TagResponse getTag(Long id);
    void deleteTag(Long id);
    List<TagResponse> listAllTags();
    List<TagResponse> listTagsByNameIn(Collection<String> names);

}
