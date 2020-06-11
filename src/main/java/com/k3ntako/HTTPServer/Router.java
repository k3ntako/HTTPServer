package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class Router {
  private HashMap<String, RouteInterface> routes;

  public Router(HashMap<String, RouteInterface> routes) {
    this.routes = routes;
  }

  public Response routeRequest(RequestInterface request){
    var route = routes.get(request.getRoute());
    return route.getResponse(request);
  }
}
