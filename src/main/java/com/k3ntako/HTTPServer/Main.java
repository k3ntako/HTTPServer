package com.k3ntako.HTTPServer;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    var echoServer = new EchoServer();
    var app = new App(echoServer);
    app.start();
  }
}
