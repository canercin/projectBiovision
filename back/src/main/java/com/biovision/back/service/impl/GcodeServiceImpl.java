package com.biovision.back.service.impl;

import com.biovision.back.service.GcodeService;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GcodeServiceImpl implements GcodeService {
    private volatile boolean paused = false;
    private volatile boolean cancelled = false;

    /**
     * Sadece dosya path'i ile G-code dosyasını Arduino'ya gönderir.
     * Tüm işlemleri (port bulma, açma, gönderme, kapama) otomatik yapar.
     * @param filePath G-code dosyasının yolu
     * @throws IOException
     */
    @Override
    public void processGCodeFile(String filePath) throws IOException {
        SerialPort serialPort = null;
        OutputStream outputStream = null;
        BufferedReader inputReader = null;
        try {
            serialPort = findArduinoPort();
            if (serialPort == null) {
                throw new IOException("Arduino seri portu bulunamadı!");
            }
            serialPort.setBaudRate(115200);
            if (!serialPort.openPort()) {
                throw new IOException("Seri port açılamadı: " + serialPort.getSystemPortName());
            }
            outputStream = serialPort.getOutputStream();
            inputReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

            // GRBL açılış mesajlarını temizle
            try {
                Thread.sleep(2000); // 2 saniye bekle
            } catch (InterruptedException ignored) {}
            while (inputReader.ready()) {
                inputReader.readLine();
            }

            List<String> gcodeLines = readGCodeFile(filePath);
            cancelled = false;
            paused = false;
            for (String command : gcodeLines) {
                // İptal kontrolü
                if (cancelled) {
                    break;
                }
                // Duraklatma kontrolü
                while (paused) {
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                    if (cancelled) break;
                }
                if (cancelled) break;
                sendCommand(command.trim(), outputStream);
                waitForOK(inputReader);
            }
        } finally {
            try { if (inputReader != null) inputReader.close(); } catch (Exception ignored) {}
            try { if (outputStream != null) outputStream.close(); } catch (Exception ignored) {}
            if (serialPort != null) serialPort.closePort();
        }
    }

    private SerialPort findArduinoPort() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            String portDesc = (port.getDescriptivePortName() + " " + port.getSystemPortName()).toLowerCase();
            if (portDesc.contains("arduino") || portDesc.contains("usb") || portDesc.contains("acm") || portDesc.contains("com")) {
                return port;
            }
        }
        return null;
    }

    private List<String> readGCodeFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        String fullFilepath = "/home/canercin/biovision/gcodes/" + filePath;
        try (BufferedReader reader = new BufferedReader(new FileReader(fullFilepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith(";") && !line.startsWith("(")) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private void sendCommand(String command, OutputStream outputStream) throws IOException {
        outputStream.write((command + "\n").getBytes());
        outputStream.flush();
    }

    private void waitForOK(BufferedReader inputReader) throws IOException {
        long start = System.currentTimeMillis();
        String line;
        while ((System.currentTimeMillis() - start) < 10000) { // 10 saniye timeout
            if (inputReader.ready() && (line = inputReader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("ok")) {
                    return;
                }
            }
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        }
        throw new IOException("GRBL'den ok cevabı alınamadı (timeout)!");
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
        this.paused = false; // iptal edilirse duraklatma da kalksın
    }
}
