package com.biovision.back.repository;

import com.biovision.back.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {
}
