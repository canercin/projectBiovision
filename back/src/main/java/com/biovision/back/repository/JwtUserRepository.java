package com.biovision.back.repository;

import com.biovision.back.entity.JwtUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JwtUserRepository extends JpaRepository<JwtUserDetails, UUID> {
    Optional<JwtUserDetails> findByUsername(String username);
}
