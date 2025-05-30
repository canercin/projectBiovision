package com.biovision.back.service.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UGSApiClient {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> uploadAndOpenGcodeFile(byte[] fileContent, String fileName) {
        String url = "http://localhost:8080/api/v1/files/uploadAndOpen";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Cookie", "csrftoken=3hQXPdGlKYz43unGGIFm2kVeTDrY6zx8");
        headers.set("Origin", "http://localhost:8080");
        headers.set("Referer", "http://localhost:8080/run");
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "tr,en;q=0.9,en-GB;q=0.8,en-US;q=0.7");
        headers.set("Connection", "keep-alive");
        headers.set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36 Edg/136.0.0.0");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"136\", \"Microsoft Edge\";v=\"136\", \"Not.A/Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource fileResource = new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };
        body.add("file", fileResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * Resume işlemi için /api/v1/files/send endpointine boş POST atar.
     */
    public ResponseEntity<String> resumeGcodeFile() {
        String url = "http://localhost:8080/api/v1/files/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "csrftoken=3hQXPdGlKYz43unGGIFm2kVeTDrY6zx8");
        headers.set("Origin", "http://localhost:8080");
        headers.set("Referer", "http://localhost:8080/run");
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "tr,en;q=0.9,en-GB;q=0.8,en-US;q=0.7");
        headers.set("Connection", "keep-alive");
        headers.set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36 Edg/136.0.0.0");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"136\", \"Microsoft Edge\";v=\"136\", \"Not.A/Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> pauseGcodeFile() {
        String url = "http://localhost:8080/api/v1/files/pause";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "csrftoken=3hQXPdGlKYz43unGGIFm2kVeTDrY6zx8");
        headers.set("Referer", "http://localhost:8080/run");
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "tr,en;q=0.9,en-GB;q=0.8,en-US;q=0.7");
        headers.set("Connection", "keep-alive");
        headers.set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36 Edg/136.0.0.0");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"136\", \"Microsoft Edge\";v=\"136\", \"Not.A/Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    public ResponseEntity<String> cancelGcodeFile() {
        String url = "http://localhost:8080/api/v1/files/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "csrftoken=3hQXPdGlKYz43unGGIFm2kVeTDrY6zx8");
        headers.set("Referer", "http://localhost:8080/run");
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "tr,en;q=0.9,en-GB;q=0.8,en-US;q=0.7");
        headers.set("Connection", "keep-alive");
        headers.set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36 Edg/136.0.0.0");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"136\", \"Microsoft Edge\";v=\"136\", \"Not.A/Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    public ResponseEntity<String> returnToZero() {
        String url = "http://localhost:8080/api/v1/machine/returnToZero";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "csrftoken=3hQXPdGlKYz43unGGIFm2kVeTDrY6zx8");
        headers.set("Referer", "http://localhost:8080/run");
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "tr,en;q=0.9,en-GB;q=0.8,en-US;q=0.7");
        headers.set("Connection", "keep-alive");
        headers.set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36 Edg/136.0.0.0");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"136\", \"Microsoft Edge\";v=\"136\", \"Not.A/Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Linux\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }
}
