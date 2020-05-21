package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

public interface EchoServerInterface {
  void createAndListen(ServerSocketWrapperInterface serverSocketWrapper);
  String readLine();
  void sendData(String data);
  void close();
}
