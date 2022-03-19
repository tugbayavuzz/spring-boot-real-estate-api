package com.realestate.model.mapper;

import com.realestate.model.dto.UserDTO;
import com.realestate.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity dtoToEntity(UserDTO dto);

    UserDTO entityToDto(UserEntity entity);

}