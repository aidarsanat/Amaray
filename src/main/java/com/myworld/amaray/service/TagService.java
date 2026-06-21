package com.myworld.amaray.service;

import com.myworld.amaray.model.Tag;
import com.myworld.amaray.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByCategory(String category) {
        return tagRepository.findByCategory(category);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тег с id " + id + " не найден"));
    }

    public Tag createTag(Tag tag) {
        if (tagRepository.existsByName(tag.getName())) {
            throw new RuntimeException("Тег '" + tag.getName() + "' уже существует");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }
}
