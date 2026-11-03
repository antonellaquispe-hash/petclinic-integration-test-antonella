package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.dtos.SpecialtyDTO;

public interface SpecialtyService {
	List<SpecialtyDTO> findAll();
	SpecialtyDTO findById(Integer id);
	SpecialtyDTO create(SpecialtyDTO specialty);
	void delete(Integer id);
	List<SpecialtyDTO> findByName(String name);
	List<SpecialtyDTO> findByOffice(String office);
}
