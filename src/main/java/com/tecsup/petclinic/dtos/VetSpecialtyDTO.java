package com.tecsup.petclinic.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetSpecialtyDTO {
    private Integer vetId;
    private Integer specialtyId;
    private String certificationDate;
    private Integer yearsExperience;
    private Boolean isPrimary;
}