package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Account;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.controllers.Admin;

public class RouteRegistrar {
  private RouteRegistry routeRegistry;

  public RouteRegistrar(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public RouteRegistry registerRoutes() {
    routeRegistry.registerGet("/simple_get", new SimpleGet());
    routeRegistry.registerGet("/simple_get_with_body", new SimpleGetWithBody());
    routeRegistry.registerGet("/admin", new Admin());
    routeRegistry.registerGet("/account", new Account());

    return routeRegistry;
  }
}
