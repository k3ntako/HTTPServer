package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.*;

public class RouteRegistrar {
  private RouteRegistry routeRegistry;
  private FileIOInterface fileIO;
  private TextFile textFile;

  public RouteRegistrar(RouteRegistry routeRegistry, FileIOInterface fileIO, TextFile textFile) {
    this.routeRegistry = routeRegistry;
    this.fileIO = fileIO;
    this.textFile = textFile;
  }

  public RouteRegistry registerRoutes() throws Exception {
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    routeRegistry.registerRoute("GET", "/simple_get_with_body", (RequestInterface req) -> new SimpleGetWithBody().get(req));
    routeRegistry.registerRoute("GET", "/admin", (RequestInterface req) -> new Admin().get(req));
    routeRegistry.registerRoute("GET", "/account", (RequestInterface req) -> new Account().get(req));
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req) -> new Reminders(textFile).post(req));
    routeRegistry.registerRoute("GET", "/reminders/:id", (RequestInterface req) -> new Reminders(textFile).get(req));
    routeRegistry.registerRoute("PATCH", "/reminders/:id", (RequestInterface req) -> new Reminders(textFile).patch(req));
    routeRegistry.registerRoute("PUT", "/reminders/:id", (RequestInterface req) -> new Reminders(textFile).put(req));
    routeRegistry.registerRoute("DELETE", "/reminders/:id", (RequestInterface req) -> new Reminders(textFile).delete(req));

    return routeRegistry;
  }
}
