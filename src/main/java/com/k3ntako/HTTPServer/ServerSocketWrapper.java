package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketWrapperInterface {
  private ServerSocket serverSocket;
  public ServerSocketWrapper(int port) {
    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Socket accept() {
    try {
      return serverSocket.accept();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void close() {
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int port() {
    return serverSocket.getLocalPort();
  }
}
