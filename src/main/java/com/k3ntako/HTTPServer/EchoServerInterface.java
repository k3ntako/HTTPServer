package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface EchoServerInterface {
  void createAndListen(ServerSocketWrapperInterface serverSocketWrapper) throws IOException;
  String readLine() throws IOException ;
  void sendData(String data);
  void close() throws IOException;
}
