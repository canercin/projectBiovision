package com.biovision.back.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Entity
@Data
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private Integer type;
}
