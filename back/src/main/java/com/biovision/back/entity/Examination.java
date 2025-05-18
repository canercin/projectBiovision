package com.biovision.back.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreatedDate
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties("examinations") // Doctor içindeki examinations alanı ignore edilir
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties({"examinations", "doctors"}) // Patient içindeki examinations ve doctors alanları ignore edilir
    private Patient patient;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("examination") // Result içindeki examination alanı ignore edilir
    private Result result;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id")
    @JsonIgnoreProperties("examinations") // Diagnosis içindeki examinations alanı ignore edilir
    private Diagnosis diagnosis;
}
