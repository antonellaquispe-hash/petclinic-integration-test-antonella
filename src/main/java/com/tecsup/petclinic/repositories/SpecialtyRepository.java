package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
	List<Specialty> findByNameContainingIgnoreCase(String name);
	List<Specialty> findByOfficeContainingIgnoreCase(String office);
}
