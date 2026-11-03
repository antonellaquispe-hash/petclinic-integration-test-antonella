package com.tecsup.petclinic.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.tecsup.petclinic.dtos.VisitDTO;

public interface VisitService {
	List<VisitDTO> findAll();
	List<VisitDTO> search(Integer petId, Integer vetId, LocalDate from, LocalDate to);
	VisitDTO findById(Long id);
	VisitDTO create(VisitDTO visit);
	VisitDTO update(Long id, VisitDTO visit);
	void delete(Long id);
	BigDecimal totalCostByPet(Integer petId);
	long countByPet(Integer petId);
}
