package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.mapper.TypeMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public List<TypeDTO> findAll() {
        List<Type> types = typeRepository.findAll();
        List<TypeDTO> dtos = new ArrayList<>();
        for (Type type : types) {
            dtos.add(typeMapper.mapToDto(type));
        }
        return dtos;
    }

    @Override
    public TypeDTO findById(Integer id) {
        Optional<Type> optional = typeRepository.findById(id);
        if (optional.isPresent()) {
            return typeMapper.mapToDto(optional.get());
        }
        return null;
    }

    @Override
    public TypeDTO create(TypeDTO typeDTO) {
        Type type = typeMapper.mapToEntity(typeDTO);
        Type saved = typeRepository.save(type);
        return typeMapper.mapToDto(saved);
    }

    @Override
    public void delete(Integer id) {
        typeRepository.deleteById(id);
    }
}