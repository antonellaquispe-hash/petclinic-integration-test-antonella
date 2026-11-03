package com.tecsup.petclinic.webs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;

@RestController
public class VisitController {
	private final VisitService visitService;

	public VisitController(VisitService visitService) {
		this.visitService = visitService;
	}

	@GetMapping("/visits")
	public ResponseEntity<List<VisitDTO>> findAll(
			@RequestParam(required = false) Integer petId,
			@RequestParam(required = false) Integer vetId,
			@RequestParam(required = false) LocalDate from,
			@RequestParam(required = false) LocalDate to) {
		if (petId == null && vetId == null && from == null && to == null) {
			return ResponseEntity.ok(visitService.findAll());
		}
		return ResponseEntity.ok(visitService.search(petId, vetId, from, to));
	}

	@GetMapping("/visits/{id}")
	public ResponseEntity<VisitDTO> findById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(visitService.findById(id));
		} catch (VisitNotFoundException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/visits")
	public ResponseEntity<VisitDTO> create(@RequestBody VisitDTO visit) {
		return ResponseEntity.status(HttpStatus.CREATED).body(visitService.create(visit));
	}

	@PutMapping("/visits/{id}")
	public ResponseEntity<VisitDTO> update(@PathVariable Long id, @RequestBody VisitDTO visit) {
		try {
			return ResponseEntity.ok(visitService.update(id, visit));
		} catch (VisitNotFoundException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/visits/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			visitService.delete(id);
			return ResponseEntity.ok().build();
		} catch (VisitNotFoundException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/visits/pet/{id}/total-cost")
	public ResponseEntity<BigDecimal> totalCostByPet(@PathVariable Integer id) {
		return ResponseEntity.ok(visitService.totalCostByPet(id));
	}

	@GetMapping("/visits/pet/{id}/count")
	public ResponseEntity<Long> countByPet(@PathVariable Integer id) {
		return ResponseEntity.ok(visitService.countByPet(id));
	}
}
