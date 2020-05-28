package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

public class Main {
  public static void main(String[] args) {
    var ioGenerator = new IOGenerator();
    var app = new EchoServer(ioGenerator);
    app.run(new ServerSocketWrapper(3000));

  }
}
