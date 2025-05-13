package com.biovision.back.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreatedDate
    private Instant date;

    private Boolean hasCancer;

    private String originalImagePath;

    private String resultImagePath;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;
}
