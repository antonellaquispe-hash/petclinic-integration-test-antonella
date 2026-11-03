package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 
 * @author jgomezm
 *
 */
@NoArgsConstructor
@Entity(name = "vets")
@Table(name = "vets")
@Data
public class Vet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String email;
	private String phone;
	private Boolean active = true;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "vet_specialties",
			joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id")
	)
	@JsonIgnore
	private Set<Specialty> specialties;

}
