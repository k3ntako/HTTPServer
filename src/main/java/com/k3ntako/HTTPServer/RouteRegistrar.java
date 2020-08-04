package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.*;

public class RouteRegistrar {
  private RouteRegistry routeRegistry;
  private FileIOInterface fileIO;
  private UUIDInterface uuid;

  public RouteRegistrar(RouteRegistry routeRegistry, FileIOInterface fileIO, UUIDInterface uuid) {
    this.routeRegistry = routeRegistry;
    this.fileIO = fileIO;
    this.uuid = uuid;
  }

  public RouteRegistry registerRoutes() throws Exception {
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    routeRegistry.registerRoute("GET", "/simple_get_with_body", (RequestInterface req) -> new SimpleGetWithBody().get(req));
    routeRegistry.registerRoute("GET", "/admin", (RequestInterface req) -> new Admin().get(req));
    routeRegistry.registerRoute("GET", "/account", (RequestInterface req) -> new Account().get(req));
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req) -> new Reminders(fileIO, uuid).post(req));
    routeRegistry.registerRoute("GET", "/text_file_content", (RequestInterface req) -> new GetTextFileContent(fileIO).get(req));

    return routeRegistry;
  }
}
