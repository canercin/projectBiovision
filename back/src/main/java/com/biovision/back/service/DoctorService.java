package com.biovision.back.service;

import com.biovision.back.dto.DoctorDTO;
import com.biovision.back.dto.request.DoctorRequest;
import com.biovision.back.entity.Doctor;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

public interface DoctorService {
    List<Doctor> findAll();
    Doctor findById(UUID id);
    Doctor save(DoctorRequest doctorRequest);
    void deleteById(UUID id);
    Doctor findByFirstName(String firstName);
    Doctor update(DoctorDTO doctorDTO);
}
