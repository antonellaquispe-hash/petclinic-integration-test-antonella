package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.VetSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetSpecialtyRepository extends JpaRepository<VetSpecialty, Integer> {
    java.util.Optional<VetSpecialty> findByVet_IdAndSpecialty_Id(Integer vetId, Integer specialtyId);
    java.util.List<VetSpecialty> findByVet_Id(Integer vetId);
    java.util.List<VetSpecialty> findBySpecialty_Id(Integer specialtyId);
}
