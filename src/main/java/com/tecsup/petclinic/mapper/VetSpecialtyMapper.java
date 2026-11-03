package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VetSpecialtyMapper {
	public VetSpecialtyDTO mapToDto(VetSpecialty assignment) {
		return new VetSpecialtyDTO(
				assignment.getVetId(),
				assignment.getSpecialtyId(),
				assignment.getCertificationDate() == null ? null : assignment.getCertificationDate().toString(),
				assignment.getYearsExperience(),
				assignment.getPrimary());
	}

	public VetSpecialty mapToEntity(VetSpecialtyDTO dto) {
		VetSpecialty assignment = new VetSpecialty(dto.getVetId(), dto.getSpecialtyId());
		assignment.setCertificationDate(dto.getCertificationDate() == null
				? null : LocalDate.parse(dto.getCertificationDate()));
		assignment.setYearsExperience(dto.getYearsExperience());
		assignment.setPrimary(dto.getIsPrimary());
		return assignment;
	}
}
