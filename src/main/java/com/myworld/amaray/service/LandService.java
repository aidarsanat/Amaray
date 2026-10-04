// src/main/java/com/myworld/amaray/service/LandService.java
package com.myworld.amaray.service;

import com.myworld.amaray.model.Land;
import com.myworld.amaray.model.Tag;
import com.myworld.amaray.repository.LandRepository;
import com.myworld.amaray.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository landRepository;
    private final TagRepository tagRepository;

    public List<Land> getAllLands() {
        return landRepository.findAll();
    }

    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Земля с id " + id + " не найдена"));
    }

    public List<Land> getLandsByFaction(Long factionId) {
        return landRepository.findByFactionId(factionId);
    }

    public List<Land> getChildLands(Long parentLandId) {
        return landRepository.findByParentLandId(parentLandId);
    }

    public Land createLand(Land land) {
        if (landRepository.existsByName(land.getName())) {
            throw new RuntimeException("Земля с именем '" + land.getName() + "' уже существует");
        }
        if (land.getTags() != null) {
            Set<Tag> fullTags = land.getTags().stream()
                    .map(tag -> tagRepository.findById(tag.getId())
                            .orElseThrow(() -> new RuntimeException("Тег с id " + tag.getId() + " не найден")))
                    .collect(Collectors.toSet());
            land.setTags(fullTags);
        }
        land.setSyncState("PENDING");
        return landRepository.save(land);
    }

    public Land updateLand(Long id, Land updatedLand) {
        Land existing = getLandById(id);
        existing.setName(updatedLand.getName());
        existing.setDescription(updatedLand.getDescription());
        existing.setFeatures(updatedLand.getFeatures());
        existing.setFaction(updatedLand.getFaction());
        existing.setParentLand(updatedLand.getParentLand());
        if (updatedLand.getTags() != null) {
            Set<Tag> fullTags = updatedLand.getTags().stream()
                    .map(tag -> tagRepository.findById(tag.getId())
                            .orElseThrow(() -> new RuntimeException("Тег с id " + tag.getId() + " не найден")))
                    .collect(Collectors.toSet());
            existing.setTags(fullTags);
        }
        existing.setSyncState("PENDING");
        return landRepository.save(existing);
    }

    public void deleteLand(Long id) {
        Land existing = getLandById(id);
        landRepository.delete(existing);
    }

    public Land markAsSynced(Long id) {
        Land land = getLandById(id);
        land.setSyncState("SYNCED");
        return landRepository.save(land);
    }

    public List<Land> getPendingLands() {
        return landRepository.findAll().stream()
                .filter(l -> "PENDING".equals(l.getSyncState()))
                .toList();
    }
}
