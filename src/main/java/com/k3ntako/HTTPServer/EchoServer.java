package com.k3ntako.HTTPServer;

import java.net.Socket;

public class EchoServer {
  private IOInterface io;

  public EchoServer(IOInterface io) {
    this.io = io;
  }

  public void run() {
    var clientSocket = io.accept();
    io.startConnection(clientSocket);

    String clientInput;
    while ((clientInput = io.readLine()) != null) {
      io.sendData(clientInput);
    }

    io.close();
  }
}
