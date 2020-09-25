package com.k3ntako.HTTPServer.wrappers;

import java.io.IOException;
import java.io.OutputStream;


public class ClientOutputStream implements ClientOutputStreamInterface {
  final private OutputStream outputStream;

  public ClientOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public void sendData(byte[] data) throws IOException {
    outputStream.write(data);
  }

  public void close() throws IOException {
    outputStream.close();
  }
}