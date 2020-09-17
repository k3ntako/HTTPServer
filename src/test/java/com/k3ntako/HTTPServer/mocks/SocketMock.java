package com.k3ntako.HTTPServer.mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketMock extends Socket {
  final private InputStream inputStream;
  final private OutputStream outputStream = new ByteArrayOutputStream();

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

  @Override
  public OutputStream getOutputStream() {
    return outputStream;
  }
}
