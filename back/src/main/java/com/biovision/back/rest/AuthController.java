package com.biovision.back.rest;

import com.biovision.back.dto.response.JwtResponse;
import com.biovision.back.dto.request.LoginRequest;
import com.biovision.back.dto.request.RegisterRequest;
import com.biovision.back.entity.JwtUserDetails;
import com.biovision.back.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtUserDetails> register(@RequestBody RegisterRequest registerRequest) {
        JwtUserDetails user = authService.register(registerRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
