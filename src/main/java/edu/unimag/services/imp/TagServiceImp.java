package edu.unimag.services.imp;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimag.api.dto.TagDTOs.*;
import edu.unimag.domain.entity.Tag;
import edu.unimag.domain.repositories.TagRepository;
import edu.unimag.exceptions.NotFoundException;
import edu.unimag.services.TagService;
import edu.unimag.services.mapper.TagMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImp implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper     tagMapper;

    @Override
    @Transactional
    public TagResponse createTag(TagCreateRequest request) {
        Tag entity = tagMapper.toEntity(request);
        Tag saved  = tagRepository.save(entity);
        return tagMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getTag(Long id) {
        return tagRepository.findById(id)
            .map(tagMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public void deleteTag(@Nonnull Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> listAllTags() {
        return tagRepository.findAll().stream()
            .map(tagMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> listTagsByNameIn(Collection<String> names) {
        return tagRepository.findByNameIn(names).stream()
            .map(tagMapper::toResponse)
            .toList();
    }
}