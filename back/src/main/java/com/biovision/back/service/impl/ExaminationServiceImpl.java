package com.biovision.back.service.impl;

import com.biovision.back.dto.ExaminationDTO;
import com.biovision.back.dto.ResultDTO;
import com.biovision.back.dto.request.ExaminationRequest;
import com.biovision.back.entity.*;
import com.biovision.back.repository.ExaminationRepository;
import com.biovision.back.repository.ResultRepository;
import com.biovision.back.security.SecurityUtils;
import com.biovision.back.service.*;
import com.biovision.back.service.mapper.impl.DiagnosisMapper;
import com.biovision.back.service.mapper.impl.ExaminationMapper;
import com.biovision.back.service.mapper.impl.ResultMapper;
import com.biovision.back.service.restTemplate.PythonApiClientService;
import com.biovision.back.service.restTemplate.dtos.response.PythonApiResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    private final ExaminationRepository examinationRepository;
    private final ExaminationMapper examinationMapper;
    private final ResultService resultService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final DiagnosisMapper diagnosisMapper;
    private final DoctorService doctorService;
    private final PythonApiClientService pythonApiClientService;
    private final ResultMapper resultMapper;
    private final ResultRepository resultRepository;

    public ExaminationServiceImpl(ExaminationRepository examinationRepository, ExaminationMapper examinationMapper, ResultService resultService, PatientService patientService, DiagnosisService diagnosisService, DiagnosisMapper diagnosisMapper, DoctorService doctorService, PythonApiClientService pythonApiClientService, ResultMapper resultMapper, ResultRepository resultRepository) {
        this.examinationRepository = examinationRepository;
        this.examinationMapper = examinationMapper;
        this.resultService = resultService;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
        this.diagnosisMapper = diagnosisMapper;
        this.doctorService = doctorService;
        this.pythonApiClientService = pythonApiClientService;
        this.resultMapper = resultMapper;
        this.resultRepository = resultRepository;
    }

    @Override
    public List<ExaminationDTO> findAll() {
        return examinationRepository.findAll()
                .stream()
                .map(examinationMapper::toDTO)
                .toList();
    }

    @Override
    public ExaminationDTO findById(UUID id) {
        return examinationRepository.findById(id)
                .map(examinationMapper::toDTO)
                .orElse(null);
    }

    @Override
    public ExaminationDTO save(ExaminationRequest examinationRequest) throws IOException {
        Patient patient = patientService.findById(examinationRequest.getPatientID());

        Diagnosis diagnosis = diagnosisService
                .findById(examinationRequest.getDiagnosisID())
                .map(diagnosisMapper::toEntity)
                .orElseThrow(() -> new IllegalArgumentException("Diagnosis not found"));

        Doctor doctor = doctorService.findById(UUID.fromString("f31786bc-057a-4e0d-8fb0-6c0a26292ee0"));

        Examination examination = new Examination();
        examination.setPatient(patient);
        examination.setDoctor(doctor);
        examination.setDiagnosis(diagnosis);

        examination = examinationRepository.save(examination);
        Result result = createResultForExamination(examinationRequest.getOriginalImage(), diagnosis.getType());
        result.setExamination(examination);
        resultRepository.save(result);
        examination.setResult(result);

        return examinationMapper.toDTO(examination);
    }

    @Override
    public ExaminationDTO update(UUID id, ExaminationDTO examinationDTO) {
        // todo will be implemented in the future
        return null;
    }

    @Override
    public void delete(UUID id) {
        examinationRepository.deleteById(id);
    }

    @Override
    public List<ExaminationDTO> findExaminationByPatientId() {
        Patient patient = (Patient) SecurityUtils.getCurrentUser();
        List<Examination> examinations = examinationRepository.findExaminationByPatientId(patient.getId());
        return examinations.stream().map(examinationMapper::toDTO).toList();
    }

    private Result createResultForExamination(MultipartFile image, int type) throws IOException {
        Result result = new Result();
        String path = saveImage(image);
        result.setOriginalImagePath(path);
        // todo set result image path and hasCancer - create py flask client
        PythonApiResponse response = pythonApiClientService.getPythonApiResponse(path, type);
        result.setResultImagePath(response.getUnet_result());
        result.setHasCancer(response.is_cancer());
        result = resultRepository.save(result);
        return result;
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IOException("Yüklenen dosya boş");
        }

        String originalFilename = image.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String filename = System.currentTimeMillis() + fileExtension;

        String homeDir = System.getProperty("user.home");
        String uploadDirectory = homeDir + "/biovision/original/";

        java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDirectory);
        if (!java.nio.file.Files.exists(uploadPath)) {
            java.nio.file.Files.createDirectories(uploadPath);
        }

        java.nio.file.Path filePath = uploadPath.resolve(filename);
        java.nio.file.Files.copy(image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }
}
