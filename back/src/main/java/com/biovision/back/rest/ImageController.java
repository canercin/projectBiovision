package com.biovision.back.rest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController("/api/image")
public class ImageController {
    @GetMapping("/original/{filename}")
    public ResponseEntity<Resource> getOriginalImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(System.getProperty("user.home") + "/biovision/original/" + filename);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/masked/{filename}")
    public ResponseEntity<Resource> getMaskedImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(System.getProperty("user.home") + "/biovision/masked_images/" + filename);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
