package com.biovision.back.service.impl;

import com.biovision.back.dto.DoctorDTO;
import com.biovision.back.dto.request.DoctorRequest;
import com.biovision.back.entity.Doctor;
import com.biovision.back.repository.DoctorRepository;
import com.biovision.back.security.enums.AuthorityConstants;
import com.biovision.back.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(UUID id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor save(DoctorRequest doctorRequest) {
        Doctor doctor = new Doctor();
        doctor.setUsername(doctorRequest.getUsername());
        doctor.setPassword(passwordEncoder.encode(doctorRequest.getPassword()));
        doctor.setRole(AuthorityConstants.ROLE_DOCTOR.name());
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        return doctorRepository.save(doctor);
    }

    public Doctor update(DoctorDTO doctorDTO){
        Doctor doctor = doctorRepository.findById(doctorDTO.getId()).orElse(null);
        if (doctor != null) {
            doctor.setFirstName(doctorDTO.getFirstName());
            doctor.setLastName(doctorDTO.getLastName());
            return doctorRepository.save(doctor);
        }
        throw new EntityNotFoundException("Doctor not found");
    }

    public void deleteById(UUID id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findByFirstName(String firstName) {
        return doctorRepository.findByFirstName(firstName).orElse(null);
    }
}
