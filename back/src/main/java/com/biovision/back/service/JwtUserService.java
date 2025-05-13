package com.biovision.back.service;

import com.biovision.back.entity.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JwtUserService {
     List<JwtUserDetails> findAll();
     Optional<JwtUserDetails> findById(UUID id);
     Optional<JwtUserDetails> findByUsername(String username);
     JwtUserDetails save(JwtUserDetails jwtUserDetails);
     void deleteById(UUID id);
     UserDetailsService getUserDetailsService();
}
