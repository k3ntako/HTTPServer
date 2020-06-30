package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.SimpleGet;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import com.k3ntako.HTTPServer.routes.SimpleRedirect;

public class RouteRegistrar {
  private RouteRegistry routeRegistry;

  public RouteRegistrar(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public RouteRegistry registerRoutes() {
    routeRegistry.registerGet("/simple_get", new SimpleGet());
    routeRegistry.registerGet("/simple_get_with_body", new SimpleGetWithBody());
    routeRegistry.registerGet("/redirect", new SimpleRedirect());

    return routeRegistry;
  }
}
