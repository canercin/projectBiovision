package com.biovision.back.service;

import com.biovision.back.dto.response.JwtResponse;
import com.biovision.back.dto.request.LoginRequest;
import com.biovision.back.dto.request.RegisterRequest;
import com.biovision.back.entity.JwtUserDetails;

public interface AuthService {
    JwtUserDetails register(RegisterRequest registerRequest);
    JwtResponse login(LoginRequest loginRequest);
}
