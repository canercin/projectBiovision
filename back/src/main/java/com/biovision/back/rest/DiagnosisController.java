package com.biovision.back.rest;

import com.biovision.back.dto.DiagnosisDTO;
import com.biovision.back.dto.request.DiagnosisRequest;
import com.biovision.back.service.DiagnosisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @PostMapping
    public DiagnosisDTO save(@RequestBody DiagnosisRequest diagnosisRequest) {
        return diagnosisService.save(diagnosisRequest);
    }

    @PutMapping
    public DiagnosisDTO update(@RequestBody DiagnosisDTO diagnosisDTO) {
        return diagnosisService.update(diagnosisDTO.getId(), diagnosisDTO);
    }

    @GetMapping
    public List<DiagnosisDTO> findAll() {
        return diagnosisService.findAll();
    }

    @GetMapping("/{id}")
    public  DiagnosisDTO findOne(@PathVariable("id") UUID id) {
        return diagnosisService.findById(id).orElse(null);
    }

    @GetMapping("/findByName/{name}")
    public DiagnosisDTO findByName(@PathVariable("name") String name) {
        return diagnosisService.findByName(name).orElse(null);
    }
}
