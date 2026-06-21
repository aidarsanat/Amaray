package com.myworld.amaray.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // К чему относится тег — RACE, FACTION, BOTH
    @Column
    private String category;
}

//SELECT r.name, t.name as tag_name
//FROM races r
//JOIN race_tags rt ON r.id = rt.race_id
//JOIN tags t ON t.id = rt.tag_id