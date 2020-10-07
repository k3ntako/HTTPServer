package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.utilities.FileTypeResolverInterface;

import java.io.InputStream;

public class FileTypeResolverMock implements FileTypeResolverInterface {
  private String returnMock;
  private Boolean guessContentTypeFromBytesCalled = false;
  private Boolean guessContentTypeFromStreamCalled = false;
  private Boolean guessContentTypeFromName = false;
  private Boolean guessContentType = false;

  public FileTypeResolverMock() {
    this.returnMock = "FileTypeMock: Mock file type not set.";
  }

  public FileTypeResolverMock(String returnMock) {
    this.returnMock = returnMock;
  }

  @Override
  public String guessContentTypeFromBytes(byte[] fileContent) {
    guessContentTypeFromBytesCalled = true;
    return returnMock;
  }

  @Override
  public String guessContentTypeFromStream(InputStream fileContent) {
    guessContentTypeFromStreamCalled = true;
    return returnMock;
  }

  @Override
  public String guessContentTypeFromName(String fileName) {
    guessContentTypeFromName = true;
    return returnMock;
  }

  @Override
  public String guessContentType(InputStream fileContent, String fileName) {
    guessContentType = true;
    return returnMock;
  }
}
