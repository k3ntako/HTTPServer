package com.k3ntako.HTTPServer;

public class App {
  private int port;
  private EchoServerInterface echoServer;

  public App(EchoServerInterface echoServerInput) {
    port = 3000;
    echoServer = echoServerInput;
  }

  public App(EchoServerInterface echoServer, int port) {
    this.port = port;
    this.echoServer = echoServer;
  }

  public void start() {
    var serverSocketWrapper = new ServerSocketWrapper(port);
    echoServer.createAndListen(serverSocketWrapper);

    String clientInput;
    while ((clientInput = echoServer.readLine()) != null) {
      echoServer.sendData(clientInput);
    }

    echoServer.close();
  }
}
