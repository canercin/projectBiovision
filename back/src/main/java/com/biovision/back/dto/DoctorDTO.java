package com.biovision.back.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class DoctorDTO extends UserDTO {
    private List<PatientDTO> patients;
}
