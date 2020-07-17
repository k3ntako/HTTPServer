package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.FileIOInterface;

import java.nio.file.Path;

public class FileIOMock implements FileIOInterface {
  private String lastWrite;
  private Path lastWritePath;
  private Path lastReadPath;
  private String mockFileContent;
  private String lastAppend;
  private Path lastAppendPath;
  private Path lastDeletePath;

  public FileIOMock() {
    this.mockFileContent = "Mock file content was not set";
  }

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

  @Override
  public void append(Path path, String appendStr) {
    lastAppendPath = path;
    lastAppend = appendStr;
  }

  @Override
  public void delete(Path path) {
    lastDeletePath = path;
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

  public Path getLastAppendPath() {
    return lastAppendPath;
  }

  public String getLastAppend() {
    return lastAppend;
  }

  public Path getLastDeletePath() {
    return lastDeletePath;
  }
}
