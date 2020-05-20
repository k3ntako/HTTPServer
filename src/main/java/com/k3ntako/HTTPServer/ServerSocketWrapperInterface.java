package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.Socket;

public interface ServerSocketWrapperInterface {
  Socket accept() throws IOException;
  void close() throws IOException;
}
