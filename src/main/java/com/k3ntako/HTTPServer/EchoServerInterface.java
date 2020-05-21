package com.k3ntako.HTTPServer;

public interface EchoServerInterface {
  void createAndListen(ServerSocketWrapperInterface serverSocketWrapper);
  String readLine();
  void sendData(String data);
  void close();
}
