package com.biovision.back.service.restTemplate.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PythonApiRequest {
    private String imagePath;
    private int type;
}
