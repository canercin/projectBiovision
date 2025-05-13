package com.biovision.back.rest;

import com.biovision.back.entity.JwtUserDetails;
import com.biovision.back.service.JwtUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jwt-users")
public class JwtUserController {

    private final JwtUserService jwtUserService;

    public JwtUserController(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @PostMapping
    public JwtUserDetails createJwtUser(@RequestBody JwtUserDetails jwtUserDetails) {
        return jwtUserService.save(jwtUserDetails);
    }

    @GetMapping
    public List<JwtUserDetails> getAllJwtUsers() {
        return jwtUserService.findAll();
    }

    @GetMapping("/{id}")
    public JwtUserDetails getJwtUserById(@PathVariable UUID id) {
        return jwtUserService.findById(id).orElse(null);
    }

    @GetMapping("/username/{username}")
    public JwtUserDetails getJwtUserByUsername(@PathVariable String username) {
        return jwtUserService.findByUsername(username).orElse(null);
    }
}
