package com.biovision.back.service.impl;

import com.biovision.back.dto.response.JwtResponse;
import com.biovision.back.dto.request.LoginRequest;
import com.biovision.back.dto.request.RegisterRequest;
import com.biovision.back.entity.JwtUserDetails;
import com.biovision.back.repository.JwtUserRepository;
import com.biovision.back.security.JwtService;
import com.biovision.back.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUserRepository jwtUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(JwtUserRepository jwtUserRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtUserRepository = jwtUserRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public JwtUserDetails register(RegisterRequest registerRequest) {
        JwtUserDetails user = new JwtUserDetails();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole("ROLE_USER");
        return jwtUserRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        JwtUserDetails user = jwtUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);

        return JwtResponse.builder().token(jwtToken).build();
    }
}
