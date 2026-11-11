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
        VetSpecialty vetSpecialty = vetSpecialtyMapper.mapToEntity(vetSpecialtyDTO);
        VetSpecialty saved = vetSpecialtyRepository.save(vetSpecialty);
        return vetSpecialtyMapper.mapToDto(saved);
    }

    @Override
    public VetSpecialtyDTO removeSpecialty(Integer vetId, Integer specId) {
        vetSpecialtyRepository.deleteById(vetId * 10 + specId);
        return null;
    }

    @Override
    public VetSpecialtyDTO setPrimary(Integer vetId, Integer specId) {
        return null;
    }

    @Override
    public VetSpecialtyDTO updateExperience(Integer vetId, Integer specId, Integer yearsExperience) {
        return null;
    }

    @Override
    public VetSpecialtyDTO updateCertification(Integer vetId, Integer specId, String certificationDate) {
        return null;
    }

    @Override
    public List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
        return java.util.Collections.emptyList();
    }

    @Override
    public List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId) {
        return java.util.Collections.emptyList();
    }
}