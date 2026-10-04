// src/main/java/com/myworld/amaray/controller/LandController.java
package com.myworld.amaray.controller;

import com.myworld.amaray.model.Land;
import com.myworld.amaray.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
public class LandController {

    private final LandService landService;

    @GetMapping
    public ResponseEntity<List<Land>> getAllLands() {
        return ResponseEntity.ok(landService.getAllLands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Land> getLandById(@PathVariable Long id) {
        return ResponseEntity.ok(landService.getLandById(id));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Land>> getPendingLands() {
        return ResponseEntity.ok(landService.getPendingLands());
    }

    // GET /api/lands/by-faction?factionId=1
    @GetMapping("/by-faction")
    public ResponseEntity<List<Land>> getLandsByFaction(@RequestParam Long factionId) {
        return ResponseEntity.ok(landService.getLandsByFaction(factionId));
    }

    // GET /api/lands/1/children — все дочерние земли (например все острова архипелага)
    @GetMapping("/{id}/children")
    public ResponseEntity<List<Land>> getChildLands(@PathVariable Long id) {
        return ResponseEntity.ok(landService.getChildLands(id));
    }

    @PostMapping
    public ResponseEntity<Land> createLand(@RequestBody Land land) {
        return ResponseEntity.status(HttpStatus.CREATED).body(landService.createLand(land));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Land> updateLand(@PathVariable Long id, @RequestBody Land land) {
        return ResponseEntity.ok(landService.updateLand(id, land));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/sync")
    public ResponseEntity<Land> markAsSynced(@PathVariable Long id) {
        return ResponseEntity.ok(landService.markAsSynced(id));
    }
}
