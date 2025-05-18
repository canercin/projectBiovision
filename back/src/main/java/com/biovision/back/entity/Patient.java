package com.biovision.back.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PATIENT")
public class Patient extends JwtUserDetails {
    @ManyToMany(mappedBy = "patients")
    @JsonIgnoreProperties("patients") // Doctor içindeki patients alanı ignore edilir
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patient_diagnosis",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id")
    )
    @JsonIgnoreProperties("patients") // Diagnosis içindeki patients alanı ignore edilir
    private List<Diagnosis> diagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"patient", "doctor"}) // Examination içindeki patient ve doctor alanları ignore edilir
    private List<Examination> examinations = new ArrayList<>();
}
