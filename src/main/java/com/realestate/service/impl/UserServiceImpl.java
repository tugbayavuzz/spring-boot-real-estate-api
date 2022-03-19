package com.realestate.service.impl;


import com.realestate.exception.DataNotFoundException;
import com.realestate.exception.RequiredFieldException;
import com.realestate.model.dto.UserDTO;
import com.realestate.model.entity.UserEntity;
import com.realestate.model.mapper.UserMapper;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.MessageResponse;
import com.realestate.model.response.UserResponse;
import com.realestate.repository.UserRepository;
import com.realestate.service.UserService;
import com.realestate.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Override
    public UserResponse getById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        UserDTO userDto = Optional.of(userRepository.findById(id))
                .get()
                .map(userMapper::entityToDto)
                .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build());

        UserResponse userResponse = UserResponse.builder().user(userDto).build();
        userResponse.addSuccessMessage();
        return userResponse;
    }

    @Override
    public UserResponse getByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.EMAIL_IS_REQUIRED)).build();
        }

        UserDTO userDto = Optional.of(userRepository.findByEmail(email))
                .get()
                .map(userMapper::entityToDto)
                .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build());

        UserResponse userResponse = UserResponse.builder().user(userDto).build();
        userResponse.addSuccessMessage();
        return userResponse;
    }

    @Override
    public UserResponse createNewUser(UserDTO userDto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(userDto.getPassword().getBytes());
            userDto.setPassword(DatatypeConverter.printHexBinary(md.digest()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        UserEntity userEntity = userMapper.dtoToEntity(userDto);
        userDto = Optional.of(userRepository.save(userEntity)).map(userMapper::entityToDto).orElse(null);

        UserResponse userResponse = UserResponse.builder().user(userDto).build();
        userResponse.addSuccessMessage();
        return userResponse;
    }

    @Override
    public UserResponse updateById(UserDTO userDto) {
        if (Objects.isNull(userDto.getId())) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.of(userRepository.findById(userDto.getId())
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(userEntity -> userRepository.save(userMapper.dtoToEntity(userDto)));

        UserResponse userResponse = UserResponse.builder().user(userDto).build();
        userResponse.addSuccessMessage();
        return userResponse;
    }

    @Override
    public BaseResponse deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.ofNullable(userRepository.findById(id)
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(userRepository::delete);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }
}
