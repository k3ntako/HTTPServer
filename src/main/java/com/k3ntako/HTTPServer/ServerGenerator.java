package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

public class ServerGenerator {
  private FileIOInterface fileIO;
  private YamlIOInterface yamlIOInterface;
  private LinkedHashMap<String, Object> config;

  public ServerGenerator(FileIOInterface fileIO, YamlIOInterface yamlIOInterface) {
    this.yamlIOInterface = yamlIOInterface;
    this.fileIO = fileIO;
  }

  public Server generate() throws Exception {
    config = this.getConfig();
    final var serverSocket = new ServerSocketWrapper((int) config.get("port"));

    var router = this.registerRoutes();
    var requestHandler = new RequestHandler(router, new RequestGenerator(), new ErrorHandler());

    return new Server(new ServerServerIO(), requestHandler, serverSocket, router);
  }

  private LinkedHashMap<String, Object> getConfig() throws Exception {
    return yamlIOInterface.read("config/server.yml");
  }

  private Router registerRoutes() throws Exception {
    var jsonIO = new JsonIO(new Gson());
    String dataDir = (String) config.get("data_directory");

    if (dataDir == null) {
      throw new Exception("Data directory was no specified");
    }

    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), dataDir + "/reminders/");
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), fileIO, reminderIO);
    var routeRegistry = routeRegistrar.registerRoutes();
    return new Router(routeRegistry);
  }
}
