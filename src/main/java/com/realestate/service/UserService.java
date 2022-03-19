package com.realestate.service;


import com.realestate.model.dto.UserDTO;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.UserResponse;

public interface UserService {

    UserResponse getById(Long id);

    UserResponse getByEmail(String email);

    UserResponse createNewUser(UserDTO userDto);

    UserResponse updateById(UserDTO userDto);

    BaseResponse deleteById(Long id);

}
