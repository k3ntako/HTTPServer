package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.*;

public class RouteRegistrar {
  private RouteRegistry routeRegistry;
  private FileIOInterface fileIO;

  public RouteRegistrar(RouteRegistry routeRegistry, FileIOInterface fileIO) {
    this.routeRegistry = routeRegistry;
    this.fileIO = fileIO;
  }

  public RouteRegistry registerRoutes() throws Exception {
    routeRegistry.registerRoute("GET","/simple_get", new SimpleGet());
    routeRegistry.registerRoute("GET","/simple_get_with_body", new SimpleGetWithBody());
    routeRegistry.registerRoute("GET","/admin", new Admin());
    routeRegistry.registerRoute("GET","/account", new Account());
    routeRegistry.registerRoute("POST","/simple_post", new SimplePost(fileIO));
    routeRegistry.registerRoute("PATCH","/simple_post", new PatchSimplePost(fileIO));
    routeRegistry.registerRoute("DELETE","/simple_post", new DeleteSimplePost(fileIO));
    routeRegistry.registerRoute("GET","/text_file_content", new GetTextFileContent(fileIO));

    return routeRegistry;
  }
}
