package com.k3ntako.HTTPServer;

import java.io.IOException;

public class App {
  private int port;
  private EchoServerInterface echoServer;

  public App(EchoServerInterface echoServerInput) {
    echoServer = echoServerInput;
  }

  public App(EchoServerInterface echoServer, int port) {
    this.port = port;
    this.echoServer = echoServer;
  }

  public void start() {
    try {
      echoServer.createAndListen(new ServerSocketWrapper(port));

      String clientInput;
      while ((clientInput = echoServer.readLine()) != null) {
        echoServer.sendData(clientInput);
      }

      echoServer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
