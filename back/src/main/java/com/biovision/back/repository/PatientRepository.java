package com.biovision.back.repository;

import com.biovision.back.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    @Query("select p from Patient p join p.doctors d join p.examinations e where d.id = :doctorId and e.doctor.id = :doctorId")
    List<Patient> getPatientsByDoctor(@Param("doctorId") UUID doctorId);
}
