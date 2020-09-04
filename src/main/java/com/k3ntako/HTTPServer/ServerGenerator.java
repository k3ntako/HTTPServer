package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

public class ServerGenerator {
  private FileIOInterface fileIO;
  private YamlIOInterface yamlIOInterface;

  public ServerGenerator(FileIOInterface fileIO, YamlIOInterface yamlIOInterface) {
    this.yamlIOInterface = yamlIOInterface;
    this.fileIO = fileIO;
  }

  public Server generate() throws Exception {
    var config = this.getConfig();
    final var serverSocket = new ServerSocketWrapper((int) config.get("port"));

    var router = this.registerRoutes();
    var requestHandler = new RequestHandler(router, new RequestGenerator(), new ErrorHandler());

    return new Server(new ServerServerIO(), requestHandler, serverSocket, router);
  }

  private LinkedHashMap<String, Object> getConfig() throws IOException, URISyntaxException {
    return yamlIOInterface.read("config/server.yml");
  }

  private Router registerRoutes() throws Exception {
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID());
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), fileIO, reminderIO);
    var routeRegistry = routeRegistrar.registerRoutes();
    return new Router(routeRegistry);
  }
}
