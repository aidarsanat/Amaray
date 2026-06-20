package com.myworld.amaray.service;

import com.myworld.amaray.model.Race;
import com.myworld.amaray.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Service и @RequiredArgsConstructor — первая аннотация говорит Spring что это сервис-класс с бизнес-логикой. Вторая это снова Lombok — он видит private final RaceRepository и автоматически создает конструктор, который принимает repository. Spring сам передаст нужный объект при запуске — это называется Dependency Injection, одна из ключевых концепций Spring.
public class RaceService {

    private final RaceRepository raceRepository;

    // Получить все расы
    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }

    // Получить одну расу по id
    public Race getRaceById(Long id) {
        return raceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Раса с id " + id + " не найдена"));
    }
//  orElseThrow — метод findById возвращает Optional<Race>, это специальная обёртка которая говорит "значение может быть, а может не быть". orElseThrow говорит: если значения нет — бросить исключение. Это лучше, чем получить NullPointerException в неожиданном месте.
//  Почему RuntimeException а не кастомный? Пока используем стандартный для простоты. Позже создадим свой EntityNotFoundException — это и будет та практика с кастомными исключениями из твоего списка.

    // Создать новую расу
    public Race createRace(Race race) {
        if (raceRepository.existsByName(race.getName())) {
            throw new RuntimeException("Раса с именем '" + race.getName() + "' уже существует");
        }
        race.setSyncStatus("PENDING");
        return raceRepository.save(race);
    }
//  setSyncStatus("PENDING") — каждый раз когда создаём или редактируем через проект, автоматически помечаем как "надо перенести в таблицу". Не нужно помнить об этом вручную.

    // Обновить расу
    public Race updateRace(Long id, Race updatedRace) {
        Race existing = getRaceById(id);
        existing.setName(updatedRace.getName());
        existing.setDescription(updatedRace.getDescription());
        existing.setType(updatedRace.getType());
        existing.setOrigin(updatedRace.getOrigin());
        existing.setFeatures(updatedRace.getFeatures());
        existing.setSyncStatus("PENDING");
        return raceRepository.save(existing);
    }

    // Удалить расу
    public void deleteRace(Long id) {
        Race existing = getRaceById(id);
        raceRepository.delete(existing);
    }

    // Пометить как синхронизированную
    public Race markAsSynced(Long id) {
        Race race = getRaceById(id);
        race.setSyncStatus("SYNCED");
        return raceRepository.save(race);
    }

    // Получить все несинхронизированные
    public List<Race> getPendingRaces() {
        return raceRepository.findAll().stream()
                .filter(r -> "PENDING".equals(r.getSyncStatus()))
                .toList();
    }
}
