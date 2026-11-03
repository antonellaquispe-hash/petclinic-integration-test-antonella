package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;

public interface VetSpecialtyService {
	VetSpecialty assignSpecialtyToVet(Integer vetId, Integer specialtyId);
	void removeSpecialtyFromVet(Integer vetId, Integer specialtyId);
}
