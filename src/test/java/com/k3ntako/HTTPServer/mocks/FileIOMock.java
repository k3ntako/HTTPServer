package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.FileIOInterface;

import java.io.IOException;
import java.nio.file.Path;

public class FileIOMock implements FileIOInterface {
  private Object lastWrite;
  private Path lastWritePath;
  private String lastPatch;
  private Path lastPatchPath;
  private String lastOverwrite;
  private Path lastOverwritePath;
  private Path lastDeletePath;
  private Path lastReadPath;
  private String lastGetResourceFileName;
  private String lastIsResourceDirectoryFileName;
  private Object[] mockFileContentArr;
  private int mockFileContentArrIdx = 0;
  private Boolean[] mockIsDirectoryArr;
  private int mockIsDirectoryArrIdx = 0;
  private IOException mockException;

  public FileIOMock() {
    this.mockFileContentArr = new String[]{"Mock file content was not set"};
    this.mockIsDirectoryArr = new Boolean[]{null};
  }

  public FileIOMock(IOException exception) {
    this.mockException = exception;
  }

  public FileIOMock(String mockFileContent) {
    this.mockFileContentArr = new String[]{mockFileContent};
  }

  public FileIOMock(byte[] mockFileContent) {
    this.mockFileContentArr = new byte[][]{mockFileContent};
  }

  public FileIOMock(String mockFileContent, Boolean mockIsDirectory) {
    this.mockFileContentArr = new String[]{mockFileContent};
    this.mockIsDirectoryArr = new Boolean[]{mockIsDirectory};
  }

  public FileIOMock(String[] mockFileContentArr, Boolean[] mockIsDirectoryArr) {
    this.mockFileContentArr = mockFileContentArr;
    this.mockIsDirectoryArr = mockIsDirectoryArr;
  }

  @Override
  public void write(Path path, String str) throws IOException {
    throwIfExceptionExists();
    lastWritePath = path;
    lastWrite = str;
  }

  @Override
  public void write(Path path, byte[] bytes) throws IOException {
    throwIfExceptionExists();
    lastWritePath = path;
    lastWrite = bytes;
  }

  @Override
  public String readString(Path path) throws IOException {
    throwIfExceptionExists();
    lastReadPath = path;

    var mockReturn = (String) mockFileContentArr[mockFileContentArrIdx];
    incrementMockContent();
    return mockReturn;
  }

  @Override
  public byte[] readAllBytes(Path path) throws IOException {
    throwIfExceptionExists();
    lastReadPath = path;

    byte[] mockReturn;

    if (mockFileContentArr[mockFileContentArrIdx] instanceof String) {
      mockReturn = ((String) mockFileContentArr[mockFileContentArrIdx]).getBytes();
    } else {
      mockReturn = (byte[]) mockFileContentArr[mockFileContentArrIdx];
    }

    incrementMockContent();
    return mockReturn;
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

    var mockReturn = (String) mockFileContentArr[mockFileContentArrIdx];
    incrementMockContent();
    return mockReturn;
  }

  @Override
  public Boolean isResourceDirectory(String fileName) {
    lastIsResourceDirectoryFileName = fileName;

    var mockReturn = mockIsDirectoryArr[mockIsDirectoryArrIdx];
    incrementIsDirectory();
    return mockReturn;
  }

  private void incrementMockContent() {
    if (mockFileContentArrIdx < mockFileContentArr.length - 1) {
      mockFileContentArrIdx++;
    }
  }

  private void incrementIsDirectory() {
    if (mockIsDirectoryArrIdx < mockIsDirectoryArr.length - 1) {
      mockIsDirectoryArrIdx++;
    }
  }

  public Object getLastWrite() {
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

  public String getLastIsResourceDirectoryFileName() {
    return lastIsResourceDirectoryFileName;
  }

  private void throwIfExceptionExists() throws IOException {
    if (mockException != null) {
      throw mockException;
    }
  }
}
