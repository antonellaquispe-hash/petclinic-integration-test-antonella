package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.mapper.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import org.springframework.stereotype.Service;
// Agrega esta import:
import java.util.List;

@Service
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetSpecialtyMapper vetSpecialtyMapper;

    public VetSpecialtyServiceImpl(VetSpecialtyRepository vetSpecialtyRepository,
                                    VetSpecialtyMapper vetSpecialtyMapper) {
        this.vetSpecialtyRepository = vetSpecialtyRepository;
        this.vetSpecialtyMapper = vetSpecialtyMapper;
    }

    @Override
    public VetSpecialtyDTO assignSpecialty(VetSpecialtyDTO vetSpecialtyDTO) {
        vetSpecialtyRepository.findByVet_IdAndSpecialty_Id(vetSpecialtyDTO.getVetId(), vetSpecialtyDTO.getSpecialtyId())
                .ifPresent(existing -> { throw new IllegalStateException("Duplicate assignment"); });
        VetSpecialty vetSpecialty = vetSpecialtyMapper.mapToEntity(vetSpecialtyDTO);
        VetSpecialty saved = vetSpecialtyRepository.save(vetSpecialty);
        return vetSpecialtyMapper.mapToDto(saved);
    }

    @Override
    public VetSpecialtyDTO removeSpecialty(Integer vetId, Integer specId) {
        VetSpecialty assignment = requireAssignment(vetId, specId);
        VetSpecialtyDTO dto = vetSpecialtyMapper.mapToDto(assignment);
        vetSpecialtyRepository.delete(assignment);
        return dto;
    }

    @Override
    public VetSpecialtyDTO setPrimary(Integer vetId, Integer specId) {
        VetSpecialty assignment = requireAssignment(vetId, specId);
        assignment.setIsPrimary(true);
        return vetSpecialtyMapper.mapToDto(vetSpecialtyRepository.save(assignment));
    }

    @Override
    public VetSpecialtyDTO updateExperience(Integer vetId, Integer specId, Integer yearsExperience) {
        VetSpecialty assignment = requireAssignment(vetId, specId);
        assignment.setYearsExperience(yearsExperience);
        return vetSpecialtyMapper.mapToDto(vetSpecialtyRepository.save(assignment));
    }

    @Override
    public VetSpecialtyDTO updateCertification(Integer vetId, Integer specId, String certificationDate) {
        VetSpecialty assignment = requireAssignment(vetId, specId);
        assignment.setCertificationDate(certificationDate);
        return vetSpecialtyMapper.mapToDto(vetSpecialtyRepository.save(assignment));
    }

    @Override
    public List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
        return vetSpecialtyRepository.findByVet_Id(vetId).stream().map(vetSpecialtyMapper::mapToDto).toList();
    }

    @Override
    public List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId) {
        return vetSpecialtyRepository.findBySpecialty_Id(specialtyId).stream().map(vetSpecialtyMapper::mapToDto).toList();
    }

    private VetSpecialty requireAssignment(Integer vetId, Integer specId) {
        return vetSpecialtyRepository.findByVet_IdAndSpecialty_Id(vetId, specId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Vet-specialty assignment not found"));
    }
}
