package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.RequestBodyParserInterface;

import java.io.BufferedReader;
import java.net.Socket;

public class RequestBodyParserMock implements RequestBodyParserInterface {
  private byte[] mockBody;
  public String contentTypeCategory;
  public int contentLength;

  public RequestBodyParserMock(String mockBody) {
    this.mockBody = mockBody.getBytes();
  }

  public RequestBodyParserMock(byte[] mockBody) {
    this.mockBody = mockBody;
  }

  @Override
  public byte[] parseBody(
      BufferedReader bufferedReader,
      Socket clientSocket,
      String contentTypeCategory,
      int contentLength
  ) {
    this.contentTypeCategory = contentTypeCategory;
    this.contentLength = contentLength;

    return mockBody;
  }
}
