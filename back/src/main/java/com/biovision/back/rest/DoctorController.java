package com.biovision.back.rest;

import com.biovision.back.dto.DoctorDTO;
import com.biovision.back.dto.request.DoctorRequest;
import com.biovision.back.entity.Doctor;
import com.biovision.back.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody DoctorRequest doctorRequest) {
        return doctorService.save(doctorRequest);
    }

    @PutMapping
    public Doctor updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.update(doctorDTO);
    }

    @GetMapping
    public List<Doctor> findAllDoctor() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public Doctor findDoctorById(@PathVariable UUID id) {
        return doctorService.findById(id);
    }

    @GetMapping("/firstName/{firstName}")
    public Doctor findDoctorByFirstName(@PathVariable String firstName) {
        return doctorService.findByFirstName(firstName);
    }
}
