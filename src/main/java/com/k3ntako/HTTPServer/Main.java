package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

public class Main {
  public static void main(String[] args) {
    var serverSocket = new ServerSocketWrapper();
    var io = new IO(serverSocket);
    var app = new EchoServer(io);
    app.run();
  }
}
