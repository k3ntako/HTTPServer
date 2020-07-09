package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.*;

public class RouteRegistrar {
  private RouteRegistry routeRegistry;

  public RouteRegistrar(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public RouteRegistry registerRoutes() {
    var fileIO = new FileIO();

    routeRegistry.registerGet("/simple_get", new SimpleGet());
    routeRegistry.registerGet("/simple_get_with_body", new SimpleGetWithBody());
    routeRegistry.registerGet("/admin", new Admin());
    routeRegistry.registerGet("/account", new Account());
    routeRegistry.registerPost("/simple_post", new SimplePost(fileIO));
    routeRegistry.registerGet("/text_file_content", new GetTextFileContent(fileIO));

    return routeRegistry;
  }
}
