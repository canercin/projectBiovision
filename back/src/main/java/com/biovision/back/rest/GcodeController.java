package com.biovision.back.rest;

import com.biovision.back.service.GcodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api/gcode")
@RestController
public class GcodeController {
    private final GcodeService gcodeService;

    public GcodeController(GcodeService gcodeService) {
        this.gcodeService = gcodeService;
    }

    @GetMapping("/process/{filename}")
    public String process(@PathVariable String filename) {
        try {
            gcodeService.processGCodeFile(filename);
            return "G-code file processed successfully.";
        } catch (IOException e) {
            return "Error processing G-code file: " + e.getMessage();
        }
    }

    @GetMapping("/pause")
    public String pause() {
        gcodeService.pause();
        return "G-code gönderme işlemi duraklatıldı.";
    }

    @GetMapping("/cancel")
    public String cancel() {
        gcodeService.cancel();
        return "G-code gönderme işlemi iptal edildi.";
    }
}
