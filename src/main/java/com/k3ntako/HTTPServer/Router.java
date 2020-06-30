package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.NotFound;

public class Router {
  private RouteRegistry routeRegistry;

  public Router(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public Response routeRequest(RequestInterface request) {
    var route = routeRegistry.get(request.getRoute());

    if (route == null) {
      route = new NotFound();
    }

    return route.getResponse(request);
  }
}
