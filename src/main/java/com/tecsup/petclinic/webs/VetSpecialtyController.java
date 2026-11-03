package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.DuplicateVetSpecialtyException;
import com.tecsup.petclinic.services.VetSpecialtyService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vet-specialties")
public class VetSpecialtyController {
	private final VetSpecialtyService service;

	public VetSpecialtyController(VetSpecialtyService service) { this.service = service; }

	@PostMapping
	public ResponseEntity<VetSpecialty> assign(@RequestBody AssignmentRequest request) {
		try {
			VetSpecialty assignment = service.assignSpecialtyToVet(request.vetId(), request.specId());
			return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
		} catch (DuplicateVetSpecialtyException | DataIntegrityViolationException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@DeleteMapping("/{vetId}/{specId}")
	public ResponseEntity<Void> remove(@PathVariable Integer vetId, @PathVariable Integer specId) {
		service.removeSpecialtyFromVet(vetId, specId);
		return ResponseEntity.ok().build();
	}

	public record AssignmentRequest(Integer vetId, Integer specId) { }
}
