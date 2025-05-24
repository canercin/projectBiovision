package com.biovision.back.service.impl;

import com.biovision.back.service.GcodeService;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GcodeServiceImpl implements GcodeService {
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

            List<String> gcodeLines = readGCodeFile(filePath);
            for (String command : gcodeLines) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
        String line;
        while ((line = inputReader.readLine()) != null) {
            if (line.trim().equalsIgnoreCase("ok")) {
                break;
            }
        }
    }
}
