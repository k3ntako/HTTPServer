package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.NotFound;

import java.util.HashMap;

public class Router {
  private Routes routes;

  public Router(Routes routes) {
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
