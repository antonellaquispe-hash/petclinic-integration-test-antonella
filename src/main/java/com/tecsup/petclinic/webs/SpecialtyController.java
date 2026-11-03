package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;

@RestController
public class SpecialtyController {
	private final SpecialtyService specialtyService;

	public SpecialtyController(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	@GetMapping("/specialties")
	public ResponseEntity<List<SpecialtyDTO>> findAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String office) {
		if (name != null) {
			return ResponseEntity.ok(specialtyService.findByName(name));
		}
		if (office != null) {
			return ResponseEntity.ok(specialtyService.findByOffice(office));
		}
		return ResponseEntity.ok(specialtyService.findAll());
	}

	@GetMapping("/specialties/{id}")
	public ResponseEntity<SpecialtyDTO> findById(@PathVariable Integer id) {
		try {
			return ResponseEntity.ok(specialtyService.findById(id));
		} catch (SpecialtyNotFoundException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/specialties")
	public ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO specialty) {
		if (specialty.getHOpen() == null || specialty.getHClose() == null
				|| specialty.getHOpen() < 0 || specialty.getHOpen() > 23
				|| specialty.getHClose() < 0 || specialty.getHClose() > 23
				|| specialty.getHOpen() >= specialty.getHClose()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.create(specialty));
	}

	@DeleteMapping("/specialties/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		try {
			specialtyService.delete(id);
			return ResponseEntity.ok().build();
		} catch (SpecialtyNotFoundException exception) {
			return ResponseEntity.notFound().build();
		}
	}
}
