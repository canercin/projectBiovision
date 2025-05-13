package com.biovision.back.rest;

import com.biovision.back.dto.request.PatientRequest;
import com.biovision.back.entity.Patient;
import com.biovision.back.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public Patient createPatient(@RequestBody PatientRequest patientRequest) {
        return patientService.save(patientRequest);
    }

    @PutMapping
    public Patient updatePatient(Patient patient) {
        return patientService.update(patient.getId(), patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable("id") UUID id) {
        patientService.deleteById(id);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") UUID id) {
        return patientService.findById(id);
    }

    @GetMapping("/{doctorId}")
    List<Patient> getPatientsByDoctor(@PathVariable("doctorId") UUID doctorId) {
        return patientService.getPatientsByDoctor(doctorId);
    }
}
