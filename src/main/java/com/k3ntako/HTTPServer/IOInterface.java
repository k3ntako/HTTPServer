package com.k3ntako.HTTPServer;

import java.net.Socket;

public interface IOInterface {
  Socket accept();
  void startConnection(Socket clientSocket);
  String readLine();
  void sendData(String data);
  void close();
}
