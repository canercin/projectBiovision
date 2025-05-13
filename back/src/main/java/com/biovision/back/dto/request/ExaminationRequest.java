package com.biovision.back.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ExaminationRequest {
    private MultipartFile originalImage;
    private UUID patientID;
    private UUID diagnosisID;
}
