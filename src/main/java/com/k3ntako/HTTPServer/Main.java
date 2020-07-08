package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

public class Main {
  public static void main(String[] args) {
    try {
      var serverIO = new ServerServerIO();
      var requestHandler = new RequestHandler();
      var serverSocket = new ServerSocketWrapper(5000);

      var routeRegistrar = new RouteRegistrar(new RouteRegistry());
      var routeRegistry = routeRegistrar.registerRoutes();
      var router = new Router(routeRegistry);

      var app = new Server(serverIO, requestHandler, serverSocket, router);

      while (true) {
        app.run();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
