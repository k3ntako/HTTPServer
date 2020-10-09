package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.fileSystemsIO.FileIOInterface;
import com.k3ntako.HTTPServer.fileSystemsIO.ReminderIO;
import com.k3ntako.HTTPServer.fileSystemsIO.YamlIOInterface;
import com.k3ntako.HTTPServer.utilities.FileExtensions;
import com.k3ntako.HTTPServer.utilities.JsonConverter;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import com.k3ntako.HTTPServer.utilities.UUID;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.util.LinkedHashMap;

public class ServerGenerator {
  final private FileIOInterface fileIO;
  final private YamlIOInterface yamlIOInterface;
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
    var clientSocketIO = new ClientSocketIO(new RequestBodyParser());

    return new Server(clientSocketIO, requestHandler, serverSocket);
  }

  private LinkedHashMap<String, Object> getConfig() throws Exception {
    return yamlIOInterface.read("config/server.yml");
  }

  private Router registerRoutes() throws Exception {
    var jsonConverter = new JsonConverter(new Gson());
    String dataDir = (String) config.get("data_directory");

    if (dataDir == null) {
      throw new Exception("Data directory was no specified");
    }

    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDir);
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), fileIO, dataDirectoryIO, reminderIO, new FileExtensions(), new MimeTypes());
    var routeRegistry = routeRegistrar.registerRoutes();
    return new Router(routeRegistry);
  }
}
