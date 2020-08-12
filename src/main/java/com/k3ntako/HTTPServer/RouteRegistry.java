package com.k3ntako.HTTPServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RouteRegistry {
  private final ArrayList<String> HTTP_METHODS = new ArrayList<>(Arrays.asList(
      "GET", "HEAD", "POST", "PUT", "DELETE",
      "CONNECT", "OPTIONS", "TRACE", "PATCH"
  ));
  private HashMap<String, HashMap<String, ControllerMethodInterface>> routes = new HashMap<>();

  public void registerRoute(String method, String url, ControllerMethodInterface controller) throws Exception {
    method = method.toUpperCase();

    if (!HTTP_METHODS.contains(method)) {
      throw new Exception("Unknown method: " + method);
    }

    var routesForMethod = routes.get(method);

    if (routesForMethod == null) {
      routesForMethod = new HashMap<>();
    }

    routesForMethod.put(url, controller);
    routes.put(method, routesForMethod);
  }

  public Route getController(RequestInterface request) {
    var routeMatcher = new RouteMatcher();
    return routeMatcher.matchRoute(routes, request);
  }
}
