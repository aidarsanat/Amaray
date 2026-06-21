package com.myworld.amaray.service;

import com.myworld.amaray.model.Faction;
import com.myworld.amaray.repository.FactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.myworld.amaray.model.Tag;
import com.myworld.amaray.repository.TagRepository;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactionService {

    private final FactionRepository factionRepository;
    private final TagRepository tagRepository;

    public List<Faction> getAllFactions() {
        return factionRepository.findAll();
    }

    public Faction getFactionById(Long id) {
        return factionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фракция с id " + id + " не найдена"));
    }

    public List<Faction> getFactionsByStatus(String status) {
        return factionRepository.findByStatus(status);
    }

    // Все фракции связанные с конкретной — например все кто присоединился к Аркану
    public List<Faction> getRelatedFactions(Long factionId) {
        getFactionById(factionId); // проверяем что фракция существует
        return factionRepository.findByRelatedFactionId(factionId);
    }

    public Faction createFaction(Faction faction) {
        if (factionRepository.existsByName(faction.getName())) {
            throw new RuntimeException("Фракция с именем '" + faction.getName() + "' уже существует");
        }
        if (faction.getTags() != null) {
            Set<Tag> fullTags = faction.getTags().stream()
                    .map(tag -> tagRepository.findById(tag.getId())
                            .orElseThrow(() -> new RuntimeException("Тег с id " + tag.getId() + " не найден")))
                    .collect(Collectors.toSet());
            faction.setTags(fullTags);
        }
        faction.setSyncState("PENDING");
        return factionRepository.save(faction);
    }

    public Faction updateFaction(Long id, Faction updatedFaction) {
        Faction existing = getFactionById(id);
        existing.setName(updatedFaction.getName());
        existing.setStatus(updatedFaction.getStatus());
        existing.setRelatedFaction(updatedFaction.getRelatedFaction());
        existing.setDescription(updatedFaction.getDescription());
        existing.setOrigin(updatedFaction.getOrigin());
        existing.setPower(updatedFaction.getPower());
        if (updatedFaction.getTags() != null) {
            Set<Tag> fullTags = updatedFaction.getTags().stream()
                    .map(tag -> tagRepository.findById(tag.getId())
                            .orElseThrow(() -> new RuntimeException("Тег с id " + tag.getId() + " не найден")))
                    .collect(Collectors.toSet());
            existing.setTags(fullTags);
        }
        existing.setSyncState("PENDING");
        return factionRepository.save(existing);
    }

    public void deleteFaction(Long id) {
        Faction existing = getFactionById(id);
        factionRepository.delete(existing);
    }

    public Faction markAsSynced(Long id) {
        Faction faction = getFactionById(id);
        faction.setSyncState("SYNCED");
        return factionRepository.save(faction);
    }

    public List<Faction> getPendingFactions() {
        return factionRepository.findAll().stream()
                .filter(f -> "PENDING".equals(f.getSyncState()))
                .toList();
    }
}
