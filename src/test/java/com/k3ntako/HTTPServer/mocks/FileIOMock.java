package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.FileIOInterface;

import java.io.IOException;
import java.nio.file.Path;

public class FileIOMock implements FileIOInterface {
  private String lastWrite;
  private Path lastWritePath;
  private String lastPatch;
  private Path lastPatchPath;
  private String lastOverwrite;
  private Path lastOverwritePath;
  private Path lastDeletePath;
  private Path lastReadPath;
  private String lastGetResourceFileName;
  private String mockFileContent;
  private IOException mockException;

  public FileIOMock() {
    this.mockFileContent = "Mock file content was not set";
  }

  public FileIOMock(IOException exception) {
    this.mockException = exception;
  }

  public FileIOMock(String mockFileContent) {
    this.mockFileContent = mockFileContent;
  }

  @Override
  public void write(Path path, String str) throws IOException {
    throwIfExceptionExists();
    lastWritePath = path;
    lastWrite = str;
  }

  @Override
  public String read(Path path) throws IOException {
    throwIfExceptionExists();
    lastReadPath = path;
    return mockFileContent;
  }

  @Override
  public void patchNewLine(Path path, String str) throws IOException {
    throwIfExceptionExists();
    lastPatchPath = path;
    lastPatch = str;
  }

  @Override
  public void overwrite(Path path, String str) throws IOException {
    throwIfExceptionExists();
    lastOverwritePath = path;
    lastOverwrite = str;
  }

  @Override
  public void delete(Path path) throws IOException {
    throwIfExceptionExists();
    lastDeletePath = path;
  }

  @Override
  public String getResource(String fileName) throws IOException {
    throwIfExceptionExists();
    lastGetResourceFileName = fileName;
    return mockFileContent;
  }

  public String getLastWrite() {
    return lastWrite;
  }

  public Path getLastWritePath() {
    return lastWritePath;
  }

  public String getLastPatch() {
    return lastPatch;
  }

  public Path getLastPatchPath() {
    return lastPatchPath;
  }

  public String getLastOverwrite() {
    return lastOverwrite;
  }

  public Path getLastOverwritePath() {
    return lastOverwritePath;
  }

  public Path getLastReadPath() {
    return lastReadPath;
  }

  public Path getLastDeletePath() {
    return lastDeletePath;
  }

  public String getLastGetResourceFileName() {
    return lastGetResourceFileName;
  }

  private void throwIfExceptionExists() throws IOException {
    if (mockException != null) {
      throw mockException;
    }
  }
}
