package com.biovision.back.service.restTemplate.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PythonApiResponse {
    private boolean isCancer;
    private String resultImagePath;
}
