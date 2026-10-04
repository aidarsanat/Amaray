// src/main/java/com/myworld/amaray/repository/LandRepository.java
package com.myworld.amaray.repository;

import com.myworld.amaray.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandRepository extends JpaRepository<Land, Long> {

    boolean existsByName(String name);

    List<Land> findByFactionId(Long factionId);

    List<Land> findByParentLandId(Long parentLandId);
}
