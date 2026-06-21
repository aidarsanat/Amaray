package com.myworld.amaray.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "factions")
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String status;

    // Ссылка на другую фракцию если статус подразумевает связь
    // например "присоединён к *Аркан*" — здесь будет id Аркана
    @ManyToOne
    @JoinColumn(name = "related_faction_id")
    private Faction relatedFaction;
//    @ManyToOne — говорит что много фракций могут ссылаться на одну другую фракцию. Например, Аркан завоевал пять мелких фракций — у каждой из пяти relatedFaction будет указывать на Аркан.
//    @JoinColumn(name = "related_faction_id") — говорит как назвать колонку в таблице. В БД появится колонка related_faction_id которая хранит id связанной фракции. Это и есть внешний ключ (Foreign Key) в SQL.

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String origin;

    @Column
    private int power;

    // Теги для типов фракции
    @ManyToMany
    @JoinTable(
            name = "faction_tags",
            joinColumns = @JoinColumn(name = "faction_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @Column
    private String syncState = "SYNCED";
}
