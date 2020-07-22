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
    routeRegistry.registerRoute("GET","/simple_get", new SimpleGet());
    routeRegistry.registerRoute("GET","/simple_get_with_body", new SimpleGetWithBody());
    routeRegistry.registerRoute("GET","/admin", new Admin());
    routeRegistry.registerRoute("GET","/account", new Account());
    routeRegistry.registerRoute("POST","/simple_post", new SimplePost(fileIO, uuid));
    routeRegistry.registerRoute("GET","/text_file_content", new GetTextFileContent(fileIO));

    return routeRegistry;
  }
}
