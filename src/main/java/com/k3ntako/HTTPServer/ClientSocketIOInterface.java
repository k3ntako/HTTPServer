package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.Socket;

public interface ClientSocketIOInterface {
  void init(Socket clientSocket) throws IOException;

  String readLine() throws IOException;

  char read() throws IOException;

  String readTextBody(int contentLength) throws IOException;

  byte[] readBinaryBody(int contentLength) throws IOException;

  void sendData(String data) throws IOException;

  void close() throws IOException;
}
