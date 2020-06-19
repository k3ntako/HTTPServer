package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.Socket;

public interface ServerIOInterface {
  void init(Socket clientSocket);
  String readLine() throws IOException;
  char read() throws IOException;
  void sendData(String data);
  void close() throws IOException;
}
