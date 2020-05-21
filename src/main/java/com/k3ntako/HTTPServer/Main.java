package com.k3ntako.HTTPServer;

public class Main {
  public static void main(String[] args) {
    var echoServer = new EchoServer();
    var app = new App(echoServer);
    app.start();
  }
}
