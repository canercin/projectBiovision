package com.biovision.back.service.impl;

import com.biovision.back.service.GcodeService;
import com.biovision.back.service.restTemplate.UGSApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GcodeServiceImpl implements GcodeService {

    @Autowired
    private UGSApiClient ugsApiClient;

    @Override
    public void processGCodeFile(String fileName) throws IOException {
        String fullFilepath = "/home/canercin/biovision/gcodes/" + fileName;
        File file = new File(fullFilepath);
        if (!file.exists()) {
            throw new IOException("Dosya bulunamadı: " + fullFilepath);
        }
        byte[] fileContent;
        try (FileInputStream fis = new FileInputStream(file)) {
            fileContent = fis.readAllBytes();
        }
        // Dosyayı uploadAndOpen endpointine gönder
        ugsApiClient.uploadAndOpenGcodeFile(fileContent, fileName);
        // Ardından resume (send) endpointine boş POST at
        ugsApiClient.resumeGcodeFile();
    }

    @Override
    public void pause() {
        ugsApiClient.pauseGcodeFile();
    }

    @Override
    public void cancel() {
        // Önce pause işlemi yapılır
        ugsApiClient.pauseGcodeFile();
        // Sonra cancel işlemi yapılır
        ugsApiClient.cancelGcodeFile();
        // Cancel işleminden sonra kısa bir bekleme ekleniyor
        try {
            Thread.sleep(1500); // 1 saniye bekle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Ardından returnToZero işlemi yapılır
        ugsApiClient.returnToZero();
    }

    @Override
    public void resume() throws IOException {
        ugsApiClient.resumeGcodeFile();
    }
}
