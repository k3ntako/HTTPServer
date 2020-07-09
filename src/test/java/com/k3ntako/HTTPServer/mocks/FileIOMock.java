package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.FileIOInterface;

import java.io.IOException;
import java.nio.file.Path;

public class FileIOMock implements FileIOInterface {
    private String lastWrite;
    private Path lastWritePath;
    private Path lastReadPath;
    private String mockFileContent;

    public FileIOMock(String mockFileContent) {
        this.mockFileContent = mockFileContent;
    }

    @Override
    public void write(Path path, String str) {
        lastWritePath = path;
        lastWrite = str;
    }

    @Override
    public String read(Path path) {
        lastReadPath = path;
        return mockFileContent;
    }

    public String getLastWrite() {
        return lastWrite;
    }

    public Path getLastWritePath() {
        return lastWritePath;
    }

    public Path getLastReadPath() {
        return lastReadPath;
    }
}
