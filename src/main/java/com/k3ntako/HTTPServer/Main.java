package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;
import org.yaml.snakeyaml.Yaml;

public class Main {
  public static void main(String[] args) throws Exception {
    var yamlIO = new YamlIO(new FileIO(), new Yaml());
    var config = yamlIO.read("config/server.yml");

    var serverIO = new ServerServerIO();
    var serverSocket = new ServerSocketWrapper((int) config.get("port"));

    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIO());
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, new RequestGenerator());

    var app = new Server(serverIO, requestHandler, serverSocket, router);

    while (true) {
      app.run();
    }
  }
}
