package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class RouteMatcher {

  public HashMap<String, Object> matchRoute(
      HashMap<String, ControllerMethodInterface> routes,
      RequestInterface request
  ) {
    var controllerMethod = routes.get(request.getRoute());

    HashMap<String, Object> returnHash = new HashMap<>();
    returnHash.put("controllerMethod", controllerMethod);
    returnHash.put("params", new HashMap<String, String>());
    return returnHash;
  }
}
