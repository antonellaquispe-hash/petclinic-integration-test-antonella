package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import java.util.List;

public interface TypeService {
    List<TypeDTO> findAll();
    TypeDTO findById(Integer id);
    TypeDTO create(TypeDTO typeDTO);
    void delete(Integer id);
}