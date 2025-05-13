package com.biovision.back.service.impl;

import com.biovision.back.entity.JwtUserDetails;
import com.biovision.back.repository.JwtUserRepository;
import com.biovision.back.service.JwtUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtUserServiceImpl implements JwtUserService {

    private final JwtUserRepository jwtUserRepository;

    public JwtUserServiceImpl(JwtUserRepository jwtUserRepository) {
        this.jwtUserRepository = jwtUserRepository;
    }

    public List<JwtUserDetails> findAll() {
        return jwtUserRepository.findAll();
    }

    public Optional<JwtUserDetails> findById(UUID id) {
        return jwtUserRepository.findById(id);
    }

    public Optional<JwtUserDetails> findByUsername(String username) {
        return jwtUserRepository.findByUsername(username);
    }

    public JwtUserDetails save(JwtUserDetails jwtUserDetails) {
        return jwtUserRepository.save(jwtUserDetails);
    }

    public void deleteById(UUID id) {
        jwtUserRepository.deleteById(id);
    }

    public UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public JwtUserDetails loadUserByUsername(String username) {
                return jwtUserRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));
            }
        };
    }
}
