package com.biovision.back.repository;

import com.biovision.back.entity.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, UUID> {
    List<Examination> findExaminationByPatientId(UUID patientId);
}
