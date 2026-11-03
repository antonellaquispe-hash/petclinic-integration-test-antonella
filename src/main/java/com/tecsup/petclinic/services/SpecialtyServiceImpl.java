package com.tecsup.petclinic.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
	private final SpecialtyRepository repository;

	public SpecialtyServiceImpl(SpecialtyRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<SpecialtyDTO> findAll() {
		return repository.findAll().stream().map(this::toDto).toList();
	}

	@Override
	public SpecialtyDTO findById(Integer id) {
		return repository.findById(id).map(this::toDto).orElseThrow(() -> new SpecialtyNotFoundException(id));
	}

	@Override
	public SpecialtyDTO create(SpecialtyDTO dto) {
		Specialty specialty = new Specialty();
		specialty.setName(dto.getName());
		specialty.setOffice(dto.getOffice());
		specialty.setHOpen(dto.getHOpen());
		specialty.setHClose(dto.getHClose());
		return toDto(repository.save(specialty));
	}

	@Override
	public void delete(Integer id) {
		if (!repository.existsById(id)) {
			throw new SpecialtyNotFoundException(id);
		}
		repository.deleteById(id);
	}

	@Override
	public List<SpecialtyDTO> findByName(String name) {
		return repository.findByNameContainingIgnoreCase(name).stream().map(this::toDto).toList();
	}

	@Override
	public List<SpecialtyDTO> findByOffice(String office) {
		return repository.findByOfficeContainingIgnoreCase(office).stream().map(this::toDto).toList();
	}

	private SpecialtyDTO toDto(Specialty specialty) {
		return SpecialtyDTO.builder().id(specialty.getId()).name(specialty.getName())
				.office(specialty.getOffice()).hOpen(specialty.getHOpen()).hClose(specialty.getHClose()).build();
	}
}
