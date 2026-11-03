package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * 
 * @author jgomezm
 *
 */
@NoArgsConstructor
@Entity(name = "visits")
@Table(name = "visits")
@Data
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "visit_date")
	private LocalDate visitDate;

	@Column(name = "description")
	private String description;

	@Column(name = "vet_id")
	private Integer vetId;

	@Column(name = "cost")
	private BigDecimal cost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id")
	//@ToString.Exclude
	private Pet pet;
}
