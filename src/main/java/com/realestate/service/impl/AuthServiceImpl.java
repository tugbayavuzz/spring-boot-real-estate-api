package com.realestate.service.impl;

import com.realestate.auth.AuthRequest;
import com.realestate.auth.AuthResponse;
import com.realestate.model.entity.UserEntity;
import com.realestate.repository.AuthRepository;
import com.realestate.service.AuthService;
import com.realestate.utils.JwtUtil;
import com.realestate.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final JwtUtil jwtUtil;

    public AuthResponse getToken(AuthRequest request) throws Exception {
        UserEntity userEntity = authRepository.findByEmail(request.getEmail());

        if (userEntity == null) {
            log.error("User not found with email " + request.getEmail());
            throw new Exception("User not found");
        }

        if (!UserUtil.isValidPassword(userEntity.getPassword(), request.getPassword())) {
            log.error("User's password not valid " + request.getEmail());
            throw new Exception("User's password not valid");
        }

        return new AuthResponse(jwtUtil.generateToken(userEntity));
    }
}
