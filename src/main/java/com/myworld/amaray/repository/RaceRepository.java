package com.myworld.amaray.repository;

import com.myworld.amaray.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
//    JpaRepository<Race, Long> — Spring автоматически даёт тебе методы save(), findAll(), findById(), delete() и т.д. Писать SQL вручную не нужно для базовых операций. existsByName — Spring сам поймёт что нужно проверить уникальность по имени, просто по названию метода.
    boolean existsByName(String name);
}
