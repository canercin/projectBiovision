package com.biovision.back.service;

import java.io.IOException;

public interface GcodeService {
    void processGCodeFile(String filePath) throws IOException;
    void pause() throws IOException;
    void cancel() throws IOException;
    void resume() throws IOException;
}
