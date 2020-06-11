package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.NotFound;

import java.util.HashMap;

public class Router {
  private HashMap<String, RouteInterface> routes;

  public Router(HashMap<String, RouteInterface> routes) {
    this.routes = routes;
  }

  public Response routeRequest(RequestInterface request){
    var route = routes.get(request.getRoute());

    if(route == null) {
      route = new NotFound();
    }

    return route.getResponse(request);
  }
}
