package com.dtvc.api.mapper;

import core.domain.User;
import core.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper extends ObjectMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDTO(User user) {
        TypeMap<User, UserDTO> typeMap = modelMapper.createTypeMap(User.class, UserDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(entity -> entity.getRole().getRoleId(),
                    UserDTO::setRoleId);
        });
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        return dto;
    }

//    public User convertToEntity(UserDTO user) {
//        TypeMap<UserDTO, User> typeMap = modelMapper.createTypeMap(UserDTO.class, User.class);
//        typeMap.addMappings(mapper -> {
//            mapper.map(dto -> dto.getRoleId(), ));
//        });
//        User entity = modelMapper.map(user, User.class);
//        return entity;
//    }

    public List<UserDTO> convertToListDTO(List<User> src) {
        TypeMap<User, UserDTO> typeMap = modelMapper.createTypeMap(User.class, UserDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(entity -> entity.getRole().getRoleId(),
                    UserDTO::setRoleId);
        });
        List<UserDTO> list = src.stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
        return list;
    }
}
