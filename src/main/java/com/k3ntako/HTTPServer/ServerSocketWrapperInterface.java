package com.k3ntako.HTTPServer;

import java.net.Socket;

public interface ServerSocketWrapperInterface {
  Socket accept();
  void close();
  int port();
}
