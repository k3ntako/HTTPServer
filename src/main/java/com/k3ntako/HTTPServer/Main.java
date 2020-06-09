package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

public class Main {
  public static void main(String[] args) {
    var ioGenerator = new IOGenerator();
    var request = new Request();
    var serverSocket = new ServerSocketWrapper(5000);
    var app = new Server(ioGenerator, request, serverSocket);

    while(true){
      app.run();
    }
  }
}
