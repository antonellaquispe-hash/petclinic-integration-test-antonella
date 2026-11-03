package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetSpecialtyRepository extends JpaRepository<VetSpecialty, VetSpecialtyId> {
	boolean existsByVetIdAndSpecialtyId(Integer vetId, Integer specialtyId);
	void deleteByVetIdAndSpecialtyId(Integer vetId, Integer specialtyId);
}
