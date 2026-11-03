package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import java.util.List;

public interface VetSpecialtyService {
    VetSpecialtyDTO assignSpecialty(VetSpecialtyDTO vetSpecialtyDTO);
    VetSpecialtyDTO removeSpecialty(Integer vetId, Integer specId);
    VetSpecialtyDTO setPrimary(Integer vetId, Integer specId);
    VetSpecialtyDTO updateExperience(Integer vetId, Integer specId, Integer yearsExperience);
    VetSpecialtyDTO updateCertification(Integer vetId, Integer specId, String certificationDate);
    List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId);
    List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId);
}