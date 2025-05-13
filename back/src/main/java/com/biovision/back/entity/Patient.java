package com.biovision.back.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PATIENT")
public class Patient extends JwtUserDetails {
    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patient_diagnosis",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id")
    )
    private List<Diagnosis> diagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Examination> examinations = new ArrayList<>();
}
