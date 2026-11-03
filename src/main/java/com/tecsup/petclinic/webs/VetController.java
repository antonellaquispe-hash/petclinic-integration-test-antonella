package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vets")
public class VetController {
	private final VetRepository repository;

	public VetController(VetRepository repository) { this.repository = repository; }

	@GetMapping
	public List<Vet> findAll() { return repository.findAll(); }

	@GetMapping("/{id}")
	public ResponseEntity<Vet> findById(@PathVariable Integer id) {
		return repository.findById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Vet> create(@RequestBody Vet vet) {
		vet.setId(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(vet));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Vet> update(@PathVariable Integer id, @RequestBody Vet input) {
		return repository.findById(id).map(existing -> {
			existing.setFirstName(input.getFirstName());
			existing.setLastName(input.getLastName());
			existing.setEmail(input.getEmail());
			existing.setPhone(input.getPhone());
			if (input.getActive() != null) existing.setActive(input.getActive());
			return ResponseEntity.ok(repository.save(existing));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		if (!repository.existsById(id)) return ResponseEntity.notFound().build();
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
