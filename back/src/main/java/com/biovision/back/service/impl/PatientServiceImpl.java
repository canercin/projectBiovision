package com.biovision.back.service.impl;

import com.biovision.back.dto.request.PatientRequest;
import com.biovision.back.entity.Doctor;
import com.biovision.back.entity.Patient;
import com.biovision.back.repository.PatientRepository;
import com.biovision.back.security.SecurityUtils;
import com.biovision.back.service.DoctorService;
import com.biovision.back.service.PatientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;

    public PatientServiceImpl(PatientRepository patientRepository, DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(UUID id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient save(PatientRequest patientRequest) {
        Patient patient = new Patient();
        patient.setUsername(patientRequest.getUsername());
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setPassword(passwordEncoder.encode(patientRequest.getPassword()));
        patient.setRole("ROLE_PATIENT");
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(UUID id, Patient patient) {
        if (patientRepository.existsById(id)) {
            patient.setId(id);
            return patientRepository.save(patient);
        }
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> getPatientsByDoctor(UUID doctorId) {
        UUID currentUserId = SecurityUtils.getCurrentUser().getId();
        return patientRepository.getPatientsByDoctor(currentUserId);
    }

    private List<Doctor> findDoctorsByPatient(UUID doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        return List.of(doctor);
    }
}
