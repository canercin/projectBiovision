package com.biovision.back.service.restTemplate;

import com.biovision.back.service.restTemplate.dtos.request.PythonApiRequest;
import com.biovision.back.service.restTemplate.dtos.response.PythonApiResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PythonApiClientService {
    private final RestTemplate restTemplate;

    public PythonApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PythonApiResponse getPythonApiResponse(String imagePath, int type) {
        Map<String, String> request = new HashMap<>();
        request.put("model_type", String.valueOf(type));
        request.put("image_path", imagePath);
        return restTemplate.postForObject("http://localhost:5000/choose_and_run_model", request, PythonApiResponse.class);
    }
}
