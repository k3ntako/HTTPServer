package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.NotFound;

import java.io.IOException;

public class Router {
  private RouteRegistry routeRegistry;

  public Router(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public Response routeRequest(RequestInterface request) throws IOException, HTTPError {

    ControllerInterface controller = routeRegistry.getController(request.getMethod(), request.getRoute());

    if (controller == null) {
      controller = new NotFound();
    }

    return controller.getResponse(request);
  }
}
