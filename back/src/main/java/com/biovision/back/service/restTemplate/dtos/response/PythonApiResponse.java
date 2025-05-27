package com.biovision.back.service.restTemplate.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PythonApiResponse {
    private String unet_result;
    private boolean is_cancer;
    private String gcode_path;
}
