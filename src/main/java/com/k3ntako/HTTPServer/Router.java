package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.NotFound;

import java.io.IOException;

public class Router {
  private RouteRegistry routeRegistry;

  public Router(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public Response routeRequest(RequestInterface request) throws IOException, HTTPError {

    Route route = routeRegistry.getController(request);
    ControllerMethodInterface controllerMethod = (RequestInterface req) -> new NotFound().handleNotFound(req);

    if(route != null) {
      controllerMethod = route.getControllerMethod();
      request.setParams(route.getParams());
    }

    return controllerMethod.getResponse(request);
  }
}
