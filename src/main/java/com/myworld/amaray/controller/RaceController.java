package com.myworld.amaray.controller;

import com.myworld.amaray.model.Race;
import com.myworld.amaray.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/races")
@RequiredArgsConstructor
//@RestController — говорит Spring что этот класс обрабатывает HTTP запросы и возвращает данные в JSON формате автоматически.
//@RequestMapping("/api/races") — базовый путь для всех методов в этом контроллере. Все эндпоинты будут начинаться с /api/races.
public class RaceController {

    private final RaceService raceService;

    // GET /api/races — все расы
    @GetMapping
    public ResponseEntity<List<Race>> getAllRaces() {
        return ResponseEntity.ok(raceService.getAllRaces());
    }

    // GET /api/races/1 — одна раса по id
    @GetMapping("/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable Long id) {
        return ResponseEntity.ok(raceService.getRaceById(id));
    }
//@PathVariable — берёт значение из URL. Когда приходит запрос GET /api/races/5, Spring автоматически достаёт 5 и передаёт в метод как id.

    // GET /api/races/pending — все несинхронизированные
    @GetMapping("/pending")
    public ResponseEntity<List<Race>> getPendingRaces() {
        return ResponseEntity.ok(raceService.getPendingRaces());
    }

    // POST /api/races — создать новую расу
    @PostMapping
    public ResponseEntity<Race> createRace(@RequestBody Race race) {
        Race created = raceService.createRace(race);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
//@RequestBody — берёт JSON из тела запроса и превращает в объект Race. Когда фронтенд отправит POST с JSON — Spring сам распарсит его в класс.
//ResponseEntity — обёртка над ответом, позволяет контролировать HTTP статус код. 200 OK, 201 CREATED, 204 NO CONTENT — это всё стандартные коды которые фронтенд ожидает.
    // PUT /api/races/1 — обновить расу
    @PutMapping("/{id}")
    public ResponseEntity<Race> updateRace(@PathVariable Long id, @RequestBody Race race) {
        return ResponseEntity.ok(raceService.updateRace(id, race));
    }

    // DELETE /api/races/1 — удалить расу
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Long id) {
        raceService.deleteRace(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/races/1/sync — пометить как синхронизированную
    @PatchMapping("/{id}/sync")
    public ResponseEntity<Race> markAsSynced(@PathVariable Long id) {
        return ResponseEntity.ok(raceService.markAsSynced(id));
    }
}
