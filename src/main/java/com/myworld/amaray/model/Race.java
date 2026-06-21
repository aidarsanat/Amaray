package com.myworld.amaray.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "races")
//@Data — это Lombok, он автоматически генерирует все getters, setters, equals(), hashCode() и toString(). Одна аннотация вместо 50 строк кода.
//@Entity — говорит Spring что этот класс = таблица в БД
//@Table — задаёт имя таблицы
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Id + @GeneratedValue — это первичный ключ, автоинкремент
    @Column(nullable = false, unique = true)
    private String name;
//    @Column — настройки колонки
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String features;

    // Связь многие-ко-многим с тегами
    @ManyToMany
    @JoinTable(
            name = "race_tags",
            joinColumns = @JoinColumn(name = "race_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @Column
    private String syncState = "SYNCED";
//    syncStatus — это наш тег из которого мы говорили. Дефолтное значение SYNCED, при создании через проект будет PENDING.
}
