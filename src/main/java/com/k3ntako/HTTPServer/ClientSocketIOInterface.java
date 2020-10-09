package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.Socket;

public interface ClientSocketIOInterface {
  void init(Socket clientSocket) throws IOException;

  String readLine() throws IOException;

  byte[] parseBody(int contentLength) throws IOException;

  void sendData(byte[] data) throws IOException;

  void close() throws IOException;
}
