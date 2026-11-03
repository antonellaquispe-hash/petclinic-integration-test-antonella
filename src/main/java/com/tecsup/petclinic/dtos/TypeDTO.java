package com.tecsup.petclinic.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO {
    private Integer id;
    private String name;
}