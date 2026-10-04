// src/main/java/com/myworld/amaray/model/Land.java
package com.myworld.amaray.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "lands")
public class Land {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String features; // особенности

    // Тип земли — из справочника Tag с category = "LAND"
    @ManyToMany
    @JoinTable(
            name = "land_tags",
            joinColumns = @JoinColumn(name = "land_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    // Принадлежность — какой фракции принадлежит земля
    @ManyToOne
    @JoinColumn(name = "faction_id")
    private Faction faction;

    // Земля может входить в состав другой земли
    // например Остров входит в Архипелаг, Архипелаг в Море
    @ManyToOne
    @JoinColumn(name = "parent_land_id")
    private Land parentLand;

    @Column
    private String syncState = "SYNCED";
}
