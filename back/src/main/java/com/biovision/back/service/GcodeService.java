package com.biovision.back.service;

import java.io.IOException;

public interface GcodeService {
    void processGCodeFile(String filePath) throws IOException;
}
