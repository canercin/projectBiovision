package com.biovision.back.rest;

import com.biovision.back.dto.ExaminationDTO;
import com.biovision.back.dto.request.ExaminationRequest;
import com.biovision.back.service.ExaminationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/examination")
public class ExaminationController {
    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ExaminationDTO createExamination(@ModelAttribute ExaminationRequest examinationRequest) throws IOException {
        return examinationService.save(examinationRequest);
    }

    @GetMapping
    public List<ExaminationDTO> getAllExaminations() {
        return examinationService.findAll();
    }

    @GetMapping("/{id}")
    public ExaminationDTO getExamination(@PathVariable("id") UUID id) {
        return examinationService.findById(id);
    }

    @GetMapping("/patient/{patientId}")
    public ExaminationDTO getExaminationByPatientId(@PathVariable("patientId") UUID patientId) {
        return examinationService.findExaminationByPatientId(patientId);
    }
}
