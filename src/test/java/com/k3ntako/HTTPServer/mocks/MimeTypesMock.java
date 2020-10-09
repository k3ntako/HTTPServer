package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.utilities.MimeTypesInterface;

import java.io.InputStream;

public class MimeTypesMock implements MimeTypesInterface {
  private String returnMock;
  private Boolean guessContentTypeFromBytesCalled = false;
  private Boolean guessContentTypeFromStreamCalled = false;
  private Boolean guessContentTypeFromName = false;
  private Boolean guessContentType = false;

  public MimeTypesMock() {
    this.returnMock = "MimeTypesMock: Mock file type not set.";
  }

  public MimeTypesMock(String returnMock) {
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
  public String guessContentType(byte[] fileBytes, String fileName) {
    guessContentType = true;
    return returnMock;
  }
}
