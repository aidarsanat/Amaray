package com.myworld.amaray.repository;

import com.myworld.amaray.model.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> {

    boolean existsByName(String name);

    // Найти все фракции с определённым статусом
    List<Faction> findByStatus(String status);

    // Найти все фракции связанные с конкретной фракцией
    List<Faction> findByRelatedFactionId(Long factionId);
}
