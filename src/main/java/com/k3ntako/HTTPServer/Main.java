package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

public class Main {
  public static void main(String[] args) {
    var ioGenerator = new IOGenerator();
    var request = new Request();
    var app = new EchoServer(ioGenerator, request);
    app.run(new ServerSocketWrapper(3000));
  }
}
