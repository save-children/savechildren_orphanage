package com.supung.orphanage.controller;

import com.supung.orphanage.model.dto.CompactOrphanageWrapperDTO;
import com.supung.orphanage.model.dto.OrphanageInputDTO;
import com.supung.orphanage.model.dto.OrphanageOutputDTO;
import com.supung.orphanage.service.OrphanageService;
import com.supung.orphanage.service.OrphanageServiceMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orphanages")
public class OrphanageController {

    private final OrphanageService orphanageService;
    private final OrphanageServiceMapper orphanageServiceMapper;


    public OrphanageController(final OrphanageService orphanageService,
                               final OrphanageServiceMapper orphanageServiceMapper) {
        this.orphanageService = orphanageService;
        this.orphanageServiceMapper = orphanageServiceMapper;
    }

    @PostMapping
    public ResponseEntity<OrphanageOutputDTO> add(@RequestBody OrphanageInputDTO orphanageInputDTO) {
        return ResponseEntity.ok(orphanageService.save(orphanageInputDTO));
    }

    @GetMapping
    @RateLimiter(name = "getAll", fallbackMethod = "getAllFallback")
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

    @GetMapping("/{id}/getWithRequirements")
    public ResponseEntity<CompactOrphanageWrapperDTO> getWithRequirements(@RequestHeader("correlation-id") String correlationId,
                                                                          @PathVariable long id) {
        log.info("Finding orphanage with requirements by orphanage id : {}, correlationId : {}", id, correlationId);
        return ResponseEntity.ok(CompactOrphanageWrapperDTO.builder()
                .orphanage(orphanageService.findById(id))
                .requirements(orphanageServiceMapper.getRequirementsByOrphanageId(correlationId, id))
                .build());
    }

    private ResponseEntity<CompactOrphanageWrapperDTO> getWithRequirementsFallback(long id, Throwable t) {
        return ResponseEntity.ok(CompactOrphanageWrapperDTO.builder()
                .orphanage(orphanageService.findById(id))
                .build());
    }

    public ResponseEntity<List<OrphanageOutputDTO>> getAllFallback(Boolean isDeleted, Throwable t) {
        return ResponseEntity.ok(null);
    }
}
