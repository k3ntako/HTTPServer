package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.SimpleGet;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import com.k3ntako.HTTPServer.routes.SimpleRedirect;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    var serverIO = new ServerServerIO();
    var requestHandler = new RequestHandler();
    var serverSocket = new ServerSocketWrapper(5000);

    var routes = new Routes();
    var router = new Router(routes);

    var app = new Server(serverIO, requestHandler, serverSocket, router);

    while(true){
      app.run();
    }
  }
}
