package com.k3ntako.HTTPServer.mocks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketMock extends Socket {
  private InputStream inputStream;

  public SocketMock() {
    inputStream = new ByteArrayInputStream("No mock content set".getBytes());
  }

  public SocketMock(String mockStr) {
    inputStream = new ByteArrayInputStream(mockStr.getBytes());
  }

  public SocketMock(byte[] mockBytes) {
    inputStream = new ByteArrayInputStream(mockBytes);
  }

  @Override
  public InputStream getInputStream() {
    return inputStream;
  }
}
