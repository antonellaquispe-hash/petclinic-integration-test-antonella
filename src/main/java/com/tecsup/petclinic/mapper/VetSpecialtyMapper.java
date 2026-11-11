package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VetSpecialtyMapper {
    VetSpecialtyMapper INSTANCE = Mappers.getMapper(VetSpecialtyMapper.class);

    @Mapping(target = "vetId", source = "vet.id")
    @Mapping(target = "specialtyId", source = "specialty.id")
    VetSpecialtyDTO mapToDto(VetSpecialty vetSpecialty);

    @Mapping(target = "vet.id", source = "vetId")
    @Mapping(target = "specialty.id", source = "specialtyId")
    VetSpecialty mapToEntity(VetSpecialtyDTO vetSpecialtyDTO);
}
