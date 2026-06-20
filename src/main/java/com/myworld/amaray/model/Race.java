package com.myworld.amaray.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private String type;

    @Column
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String features;

    @Column
    private String syncStatus = "SYNCED";
//    syncStatus — это наш тег из которого мы говорили. Дефолтное значение SYNCED, при создании через проект будет PENDING.
}
