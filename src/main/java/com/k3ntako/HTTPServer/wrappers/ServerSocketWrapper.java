package com.k3ntako.HTTPServer.wrappers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketWrapperInterface {
  private ServerSocket serverSocket;
  public ServerSocketWrapper() {
    try {
      serverSocket = new ServerSocket(3000); // defaults to 3000
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
