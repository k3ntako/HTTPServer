package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

public class Main {
  public static void main(String[] args) throws Exception {
    var serverIO = new ServerServerIO();
    var serverSocket = new ServerSocketWrapper(5000);

    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIO());
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, new RequestGenerator());
    requestHandler.useErrorHandler(new ErrorHandler());

    var app = new Server(serverIO, requestHandler, serverSocket, router);

    while (true) {
      app.run();
    }
  }
}
