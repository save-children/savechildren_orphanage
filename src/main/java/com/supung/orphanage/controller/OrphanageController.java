package com.supung.orphanage.controller;

import com.supung.orphanage.model.dto.OrphanageInputDTO;
import com.supung.orphanage.model.dto.OrphanageOutputDTO;
import com.supung.orphanage.service.OrphanageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orphanages")
public class OrphanageController {

    private OrphanageService orphanageService;

    public OrphanageController(OrphanageService orphanageService) {
        this.orphanageService = orphanageService;
    }

    @PostMapping
    public ResponseEntity<OrphanageOutputDTO> add(@RequestBody OrphanageInputDTO orphanageInputDTO) {
        return ResponseEntity.ok(orphanageService.save(orphanageInputDTO));
    }

    @GetMapping
    public ResponseEntity<List<OrphanageOutputDTO>> getAll(@RequestParam(required = false) Boolean isDeleted) {
        return ResponseEntity.ok(orphanageService.findAll(isDeleted));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrphanageOutputDTO> getById(@PathVariable long id) {
        return ResponseEntity.ok(orphanageService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrphanageOutputDTO> update(@PathVariable long id, @RequestBody OrphanageInputDTO orphanageInputDTO) {
        return ResponseEntity.ok(orphanageService.update(id, orphanageInputDTO));
    }

    @DeleteMapping("/{id}")
    public OrphanageOutputDTO delete(@PathVariable long id) {
        return orphanageService.inactivate(id);
    }
}
