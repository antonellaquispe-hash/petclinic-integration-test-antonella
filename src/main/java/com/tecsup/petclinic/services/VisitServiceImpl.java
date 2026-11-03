package com.tecsup.petclinic.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;

@Service
public class VisitServiceImpl implements VisitService {
	private final VisitRepository visitRepository;
	private final PetRepository petRepository;

	public VisitServiceImpl(VisitRepository visitRepository, PetRepository petRepository) {
		this.visitRepository = visitRepository;
		this.petRepository = petRepository;
	}

	@Override
	public List<VisitDTO> findAll() {
		return visitRepository.findAll().stream().map(this::toDto).toList();
	}

	@Override
	public List<VisitDTO> search(Integer petId, Integer vetId, LocalDate from, LocalDate to) {
		return visitRepository.search(petId, vetId, from, to).stream().map(this::toDto).toList();
	}

	@Override
	public VisitDTO findById(Long id) {
		return visitRepository.findById(id).map(this::toDto).orElseThrow(() -> new VisitNotFoundException(id));
	}

	@Override
	public VisitDTO create(VisitDTO dto) {
		return toDto(visitRepository.save(toEntity(dto, new Visit())));
	}

	@Override
	public VisitDTO update(Long id, VisitDTO dto) {
		Visit visit = visitRepository.findById(id).orElseThrow(() -> new VisitNotFoundException(id));
		return toDto(visitRepository.save(toEntity(dto, visit)));
	}

	@Override
	public void delete(Long id) {
		if (!visitRepository.existsById(id)) {
			throw new VisitNotFoundException(id);
		}
		visitRepository.deleteById(id);
	}

	@Override
	public BigDecimal totalCostByPet(Integer petId) {
		return visitRepository.totalCostByPet(petId);
	}

	@Override
	public long countByPet(Integer petId) {
		return visitRepository.countByPet_Id(petId);
	}

	private Visit toEntity(VisitDTO dto, Visit visit) {
		Pet pet = petRepository.findById(dto.getPetId())
				.orElseThrow(() -> new IllegalArgumentException("Pet not found: " + dto.getPetId()));
		visit.setPet(pet);
		visit.setVetId(dto.getVetId());
		visit.setVisitDate(LocalDate.parse(dto.getVisitDate()));
		visit.setDescription(dto.getDescription());
		visit.setCost(dto.getCost());
		return visit;
	}

	private VisitDTO toDto(Visit visit) {
		return VisitDTO.builder().id(visit.getId()).petId(visit.getPet().getId()).vetId(visit.getVetId())
				.visitDate(visit.getVisitDate().toString()).description(visit.getDescription()).cost(visit.getCost()).build();
	}
}
