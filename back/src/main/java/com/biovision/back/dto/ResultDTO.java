package com.biovision.back.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ResultDTO {
    private UUID id;
    private Instant date;
    private Boolean hasCancer;
    private String originalImagePath;
    private String resultImagePath;
}
