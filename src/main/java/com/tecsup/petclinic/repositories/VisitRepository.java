package com.tecsup.petclinic.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
	@Query("select v from visits v where (:petId is null or v.pet.id = :petId) "
			+ "and (:vetId is null or v.vetId = :vetId) "
			+ "and (:fromDate is null or v.visitDate >= :fromDate) "
			+ "and (:toDate is null or v.visitDate <= :toDate) order by v.id")
	List<Visit> search(@Param("petId") Integer petId, @Param("vetId") Integer vetId,
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

	long countByPet_Id(Integer petId);

	@Query("select coalesce(sum(v.cost), 0) from visits v where v.pet.id = :petId")
	BigDecimal totalCostByPet(@Param("petId") Integer petId);
}
