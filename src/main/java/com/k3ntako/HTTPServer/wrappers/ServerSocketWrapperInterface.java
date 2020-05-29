package com.k3ntako.HTTPServer.wrappers;

import java.net.Socket;

public interface ServerSocketWrapperInterface {
  Socket accept();
  void close();
}
