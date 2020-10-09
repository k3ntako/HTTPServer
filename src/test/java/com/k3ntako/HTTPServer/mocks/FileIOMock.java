package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.fileSystemsIO.FileIOInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
  private Path lastListFilesPath;
  private String lastGetResourceFileName;
  private String lastGetResourceIfExistsFileName;
  private byte[][] mockFileContentArr;
  private int mockFileContentArrIdx = 0;
  private IOException mockException;
  private File[] mockListFilesReturn;

  public FileIOMock() {
    this.mockFileContentArr = new byte[][]{"Mock file content was not set".getBytes()};
  }

  public FileIOMock(IOException exception) {
    this.mockException = exception;
  }

  public FileIOMock(String mockFileContent) {
    if (mockFileContent == null) {
      this.mockFileContentArr = new byte[][]{null};
    } else {
      this.mockFileContentArr = new byte[][]{mockFileContent.getBytes()};
    }
  }

  public FileIOMock(byte[] mockFileContent, File[] mockListFilesReturn) {
    this.mockFileContentArr = new byte[][]{mockFileContent};
    this.mockListFilesReturn = mockListFilesReturn;
  }

  public FileIOMock(byte[] mockFileContent) {
    this.mockFileContentArr = new byte[][]{mockFileContent};
  }

  public FileIOMock(byte[][] mockFileContentArr) {
    this.mockFileContentArr = mockFileContentArr;
  }

  public FileIOMock(String[] mockFileContentArr) {
    var byteArrays = new byte[mockFileContentArr.length][];
    for (int i = 0; i < mockFileContentArr.length; i++) {
      var mockStr = mockFileContentArr[i];
      if (mockStr == null) {
        byteArrays[i] = null;
      } else {
        byteArrays[i] = mockStr.getBytes();
      }
    }
    this.mockFileContentArr = byteArrays;
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

    var mockReturn = mockFileContentArr[mockFileContentArrIdx];
    incrementMockContent();

    if (mockReturn == null) {
      return null;
    }

    return new String(mockReturn);
  }

  @Override
  public byte[] readAllBytes(Path path) throws IOException {
    throwIfExceptionExists();
    lastReadPath = path;

    byte[] mockReturn = mockFileContentArr[mockFileContentArrIdx];

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
    lastGetResourceFileName = fileName;
    throwIfExceptionExists();

    var mockReturn = mockFileContentArr[mockFileContentArrIdx];
    incrementMockContent();
    return new String(mockReturn);
  }

  @Override
  public byte[] getResourceIfExists(String fileName) {
    lastGetResourceIfExistsFileName = fileName;

    var mockReturn = mockFileContentArr[mockFileContentArrIdx];
    incrementMockContent();
    return mockReturn;
  }

  @Override
  public File[] listFiles(Path path) {
    lastListFilesPath = path;

    return mockListFilesReturn;
  }

  private void incrementMockContent() {
    if (mockFileContentArrIdx < mockFileContentArr.length - 1) {
      mockFileContentArrIdx++;
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

  public String getLastGetResourceIfExistsFileName() {
    return lastGetResourceIfExistsFileName;
  }

  public Path getLastListFilesPath() {
    return lastListFilesPath;
  }

  private void throwIfExceptionExists() throws IOException {
    if (mockException != null) {
      throw mockException;
    }
  }
}
