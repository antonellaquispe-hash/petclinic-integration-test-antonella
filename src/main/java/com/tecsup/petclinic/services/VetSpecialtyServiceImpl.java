package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.DuplicateVetSpecialtyException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import org.springframework.stereotype.Service;

@Service
public class VetSpecialtyServiceImpl implements VetSpecialtyService {
	private final VetSpecialtyRepository repository;

	public VetSpecialtyServiceImpl(VetSpecialtyRepository repository) { this.repository = repository; }

	@Override
	public VetSpecialty assignSpecialtyToVet(Integer vetId, Integer specialtyId) {
		if (repository.existsByVetIdAndSpecialtyId(vetId, specialtyId)) {
			throw new DuplicateVetSpecialtyException("La especialidad ya está asignada a este veterinario");
		}
		return repository.save(new VetSpecialty(vetId, specialtyId));
	}

	@Override
	public void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) {
		repository.deleteByVetIdAndSpecialtyId(vetId, specialtyId);
	}
}
