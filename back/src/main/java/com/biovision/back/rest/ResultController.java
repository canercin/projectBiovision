package com.biovision.back.rest;

import com.biovision.back.dto.ResultDTO;
import com.biovision.back.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/result")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public List<ResultDTO> getAllResults() {
        return resultService.findAll();
    }

    @GetMapping("/{id}")
    public ResultDTO getResult(@PathVariable("id") UUID id) {
        return resultService.findById(id);
    }
}
