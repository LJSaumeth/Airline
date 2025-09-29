package edu.unimag.services.imp;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unimag.api.dto.TagDTOs.*;
import edu.unimag.domain.repositories.TagRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.TagService;
import edu.unimag.services.mapper.TagMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class TagServiceImp implements TagService {
	private final TagRepository tagRepository;

    @Override
    public TagResponse createTag(TagCreateRequest request) {
        var tag = TagMapper.toEntity(request);
        return TagMapper.toResponse(tagRepository.save(tag));
    }

    @Override
    public TagResponse getTag(Long id) {
        return tagRepository.findById(id).map(TagMapper::toResponse).orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(id)));
    }

    @Override
    public void deleteTag(@Nonnull Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<TagResponse> listAllTags() {
        return tagRepository.findAll().stream().map(TagMapper::toResponse).toList();
    }

    @Override
    public List<TagResponse> listTagsByNameIn(Collection<String> names) {
        return tagRepository.findByNameIn(names).stream().map(TagMapper::toResponse).toList();
    }
}
