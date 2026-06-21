package com.myworld.amaray.controller;

import com.myworld.amaray.model.Faction;
import com.myworld.amaray.service.FactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factions")
@RequiredArgsConstructor
public class FactionController {

    private final FactionService factionService;

    @GetMapping
    public ResponseEntity<List<Faction>> getAllFactions() {
        return ResponseEntity.ok(factionService.getAllFactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faction> getFactionById(@PathVariable Long id) {
        return ResponseEntity.ok(factionService.getFactionById(id));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Faction>> getPendingFactions() {
        return ResponseEntity.ok(factionService.getPendingFactions());
    }

    // GET /api/factions?status=распалось
    @GetMapping("/by-status")
    public ResponseEntity<List<Faction>> getFactionsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(factionService.getFactionsByStatus(status));
    }

    // GET /api/factions/1/related — все фракции связанные с фракцией 1
    @GetMapping("/{id}/related")
    public ResponseEntity<List<Faction>> getRelatedFactions(@PathVariable Long id) {
        return ResponseEntity.ok(factionService.getRelatedFactions(id));
    }

    @PostMapping
    public ResponseEntity<Faction> createFaction(@RequestBody Faction faction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(factionService.createFaction(faction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faction> updateFaction(@PathVariable Long id, @RequestBody Faction faction) {
        return ResponseEntity.ok(factionService.updateFaction(id, faction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaction(@PathVariable Long id) {
        factionService.deleteFaction(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/sync")
    public ResponseEntity<Faction> markAsSynced(@PathVariable Long id) {
        return ResponseEntity.ok(factionService.markAsSynced(id));
    }
}
