package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.SimpleGet;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    var ioGenerator = new IOGenerator();
    var requestHandler = new RequestHandler();
    var serverSocket = new ServerSocketWrapper(5000);

    var routes = new HashMap<String, RouteInterface>();
    routes.put("/simple_get", new SimpleGet());
    routes.put("/simple_get_with_body", new SimpleGetWithBody());
    var router = new Router(routes);

    var app = new Server(ioGenerator, requestHandler, serverSocket, router);

    while(true){
      app.run();
    }
  }
}
