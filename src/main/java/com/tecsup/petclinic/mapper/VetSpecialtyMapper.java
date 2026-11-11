package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VetSpecialtyMapper {
    VetSpecialtyMapper INSTANCE = Mappers.getMapper(VetSpecialtyMapper.class);

    VetSpecialtyDTO mapToDto(VetSpecialty vetSpecialty);

    VetSpecialty mapToEntity(VetSpecialtyDTO vetSpecialtyDTO);
}