package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vet_specialties")
public class VetSpecialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private com.tecsup.petclinic.entities.Vet vet;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private com.tecsup.petclinic.entities.Specialty specialty;

    @Column(name = "certification_date")
    private String certificationDate;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "is_primary")
    private Boolean isPrimary;
}