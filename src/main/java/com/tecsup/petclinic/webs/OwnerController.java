package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
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
@RequestMapping("/owners")
public class OwnerController {
	private final OwnerRepository repository;

	public OwnerController(OwnerRepository repository) { this.repository = repository; }

	@GetMapping
	public List<Owner> findAll() { return repository.findAll(); }

	@GetMapping("/{id}")
	public ResponseEntity<Owner> findById(@PathVariable Long id) {
		return repository.findById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Owner> create(@RequestBody Owner owner) {
		owner.setId(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(owner));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Owner> update(@PathVariable Long id, @RequestBody Owner input) {
		return repository.findById(id).map(existing -> {
			existing.setFirstName(input.getFirstName());
			existing.setLastName(input.getLastName());
			existing.setAddress(input.getAddress());
			existing.setCity(input.getCity());
			existing.setTelephone(input.getTelephone());
			return ResponseEntity.ok(repository.save(existing));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!repository.existsById(id)) return ResponseEntity.notFound().build();
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
