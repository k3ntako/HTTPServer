package com.k3ntako.HTTPServer;

import java.io.IOException;

public class App {
  private EchoServerInterface echoServer;
  public App(EchoServerInterface echoServerInput) {
    echoServer = echoServerInput;
  }

  public void start() throws IOException {
    echoServer.createAndListen(new ServerSocketWrapper(3000));

    var inputtedStr = echoServer.readLine();
    System.out.println(inputtedStr);
    echoServer.sendData(inputtedStr);
    echoServer.close();
  }
}