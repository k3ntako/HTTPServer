package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface EchoServerInterface {
  void createAndListen(ServerSocketWrapperInterface serverSocketWrapper);
  String readLine();
  void sendData(String data);
  void close();
}
