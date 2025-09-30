package edu.unimag.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unimag.api.dto.TagDTOs.*;
import edu.unimag.domain.entity.Tag;
import edu.unimag.domain.repositories.TagRepository;
import edu.unimag.services.imp.TagServiceImp;

@ExtendWith(MockitoExtension.class)
public class TagServiceImpTest {
	@Mock TagRepository tagRepository;

    @InjectMocks TagServiceImp tagService;

    @Test
    void shouldCreateTagAndMapToResponse(){
        when(tagRepository.save(any())).thenAnswer(invocation -> {
            Tag tag = invocation.getArgument(0);
            tag.setId(1L); tag.setName("Tag1");
            return tag;
        });

        var response = tagService.createTag(new TagCreateRequest("Tag1"));

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Tag1");
    }

    @Test
    void shouldListAllTags(){
        when(tagRepository.findAll()).thenReturn(List.of(
                Tag.builder().id(1L).name("pruebi-tag").build(),
                Tag.builder().id(2L).name("tag-xd").build(),
                Tag.builder().id(3L).name("tag-zzz").build(),
                Tag.builder().id(4L).name("miTag").build()
        ));

        var response = tagService.listAllTags();

        assertThat(response.size()).isEqualTo(4);
        assertThat(response).extracting(TagResponse::name).containsExactly("pruebi-tag", "tag-xd", "tag-zzz", "miTag");
    }

    @Test
    void shouldListTagsByNameIn(){
        var tagNames = List.of("pruebi-tag", "miTag");
        when(tagRepository.findByNameIn(tagNames)).thenReturn(List.of(
                Tag.builder().id(1L).name("pruebi-tag").build(),
                Tag.builder().id(4L).name("miTag").build()
        ));

        var response = tagService.listTagsByNameIn(tagNames);

        assertThat(response.size()).isEqualTo(2);
        assertThat(response).extracting(TagResponse::name).containsExactly("pruebi-tag", "miTag");
    }
}
