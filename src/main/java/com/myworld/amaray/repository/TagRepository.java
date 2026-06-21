package com.myworld.amaray.repository;

import com.myworld.amaray.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    List<Tag> findByCategory(String category);
}
