package com.biovision.back.service;

import com.biovision.back.dto.request.PatientRequest;
import com.biovision.back.entity.Patient;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    List<Patient> findAll();
    Patient findById(UUID id);
    Patient save(PatientRequest patientRequest);
    Patient update(UUID id, Patient patient);
    void deleteById(UUID id);
    List<Patient> getPatientsByDoctor(UUID doctorId);
}
