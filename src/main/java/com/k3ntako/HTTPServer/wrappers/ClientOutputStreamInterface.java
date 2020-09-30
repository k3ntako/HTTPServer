package com.k3ntako.HTTPServer.wrappers;

import java.io.IOException;

public interface ClientOutputStreamInterface {
  void sendData(byte[] data) throws IOException;

  void close() throws IOException;
}