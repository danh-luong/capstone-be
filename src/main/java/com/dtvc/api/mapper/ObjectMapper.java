package com.dtvc.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapper<D, E> {

    @Autowired
    private ModelMapper modelMapper;

    public D convertToDTO(Object src, Class<D> destination) {
        D dto = modelMapper.map(src, destination);
        return dto;
    }

    public E convertToEntity(Object src, Class<E> destination) {
        E entity = modelMapper.map(src, destination);
        return entity;
    }

    public List<D> convertToListDTO(List<E> src, Class<D> destination) {
        List<D> list = src.stream().map(entity -> modelMapper.map(entity, destination)).collect(Collectors.toList());
        return list;
    }

    public List<E> convertToListEntity(List<D> src, Class<E> destination) {
        List<E> list = src.stream().map(dto -> modelMapper.map(dto, destination)).collect(Collectors.toList());
        return list;
    }
}
