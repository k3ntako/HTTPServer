package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.NotFound;

import java.io.IOException;
import java.util.HashMap;

public class Router {
  private RouteRegistry routeRegistry;

  public Router(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public Response routeRequest(RequestInterface request) throws IOException, HTTPError {

    HashMap<String, Object> hash = routeRegistry.getController(request.getMethod(), request.getRoute());

    ControllerMethodInterface controllerMethod = null;
    if(hash != null){
      controllerMethod = (ControllerMethodInterface) hash.get("controllerMethod");
      request.setParams((HashMap<String, String>) hash.get("params"));
    }

    if(controllerMethod == null){
      controllerMethod = (RequestInterface req) -> new NotFound().handleNotFound(req);
    }

    return controllerMethod.getResponse(request);
  }
}
