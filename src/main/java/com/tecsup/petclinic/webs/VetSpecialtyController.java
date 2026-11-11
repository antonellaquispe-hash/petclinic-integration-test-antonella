package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.services.VetSpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.*;
// Agrega estas imports:
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.dao.DataIntegrityViolationException;


@RestController
@RequestMapping("/vet-specialties")
public class VetSpecialtyController {

    private final VetSpecialtyService vetSpecialtyService;

    public VetSpecialtyController(VetSpecialtyService vetSpecialtyService) {
        this.vetSpecialtyService = vetSpecialtyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetSpecialtyDTO> assignSpecialty(@RequestBody VetSpecialtyDTO vetSpecialtyDTO) {
        try {
            VetSpecialtyDTO created = vetSpecialtyService.assignSpecialty(vetSpecialtyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalStateException | DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{vetId}/{specId}")
    public ResponseEntity<Void> removeSpecialty(@PathVariable Integer vetId, @PathVariable Integer specId) {
        vetSpecialtyService.removeSpecialty(vetId, specId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{vetId}/{specId}/primary")
    public ResponseEntity<VetSpecialtyDTO> setPrimary(@PathVariable Integer vetId, @PathVariable Integer specId) {
        VetSpecialtyDTO dto = vetSpecialtyService.setPrimary(vetId, specId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{vetId}/{specId}/experience")
    public ResponseEntity<VetSpecialtyDTO> updateExperience(
            @PathVariable Integer vetId, @PathVariable Integer specId,
            @RequestBody JsonNode body) {
        Integer yearsExperience = body.isNumber() ? body.asInt() : body.path("yearsExperience").asInt();
        VetSpecialtyDTO dto = vetSpecialtyService.updateExperience(vetId, specId, yearsExperience);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{vetId}/{specId}/certification")
    public ResponseEntity<VetSpecialtyDTO> updateCertification(
            @PathVariable Integer vetId, @PathVariable Integer specId,
            @RequestBody JsonNode body) {
        String certificationDate = body.isTextual() ? body.asText() : body.path("certificationDate").asText();
        VetSpecialtyDTO dto = vetSpecialtyService.updateCertification(vetId, specId, certificationDate);
        return ResponseEntity.ok(dto);
    }
}
