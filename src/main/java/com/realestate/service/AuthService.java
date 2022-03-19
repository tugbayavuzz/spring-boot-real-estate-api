package com.realestate.service;

import com.realestate.auth.AuthRequest;
import com.realestate.auth.AuthResponse;

public interface AuthService {

    AuthResponse getToken(AuthRequest request) throws Exception;
}
