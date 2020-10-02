package com.k3ntako.HTTPServer;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteMatcher {

  public Route matchRoute(
      HashMap<String, HashMap<String, ControllerMethodInterface>> routes,
      RequestInterface request
  ) {
    var verb = request.getMethod();
    var routesForMethod = routes.get(verb);

    if (routesForMethod == null) {
      return null;
    }

    var exactMatch = this.getExactMatch(routesForMethod, request);

    if (exactMatch != null) {
      return exactMatch;
    }

    return getVariableRoute(routesForMethod, request.getRoute());
  }

  private Route getExactMatch(
      HashMap<String, ControllerMethodInterface> routes,
      RequestInterface request
  ) {
    var controllerMethod = routes.get(request.getRoute());

    if (controllerMethod != null) {
      var route = new Route(request.getRoute());
      route.setControllerMethod(controllerMethod);

      return route;
    }

    return null;
  }


  private Route getVariableRoute(
      HashMap<String, ControllerMethodInterface> routesForMethod,
      String requestRoute
  ) {
    var route = new Route(requestRoute);
    var requestParts = convertToArray(requestRoute);

    for (String registeredRoute : routesForMethod.keySet()) {
      var registeredParts = convertToArray(registeredRoute);

      var doPartsMatch = partsMatch(registeredParts, requestParts);
      if (doPartsMatch) {
        var controllerMethod = routesForMethod.get(registeredRoute);
        if (controllerMethod == null) {
          return null;
        }

        route.setControllerMethod(controllerMethod);
        route.setRouteParams(this.parseRouteParams(registeredRoute, requestRoute));
        return route;
      }
    }

    return null;
  }

  private String[] convertToArray(String route) {
    var strArr = route.split("/");

    return removeBlanks(strArr);
  }

  private String[] removeBlanks(String[] strArr) {
    ArrayList<String> arrayList = new ArrayList<>();

    for (String str : strArr) {
      if (!str.isBlank()) {
        arrayList.add(str);
      }
    }

    String[] newStrArr = new String[arrayList.size()];
    return arrayList.toArray(newStrArr);
  }

  private boolean partsMatch(String[] registeredParts, String[] requestParts) {
    for (int i = 0; i < registeredParts.length; i++) {
      var registeredPart = registeredParts[i];
      var requestPart = requestParts[i];

      if (registeredPart.equals("*")) {
        continue;
      }

      if (registeredPart.charAt(0) == ':') {
        continue;
      }

      if (!registeredPart.equals(requestPart)) {
        return false;
      }
    }

    return true;
  }

  private HashMap<String, String> parseRouteParams(String registeredRoute, String requestRoute) {
    var routeParams = new HashMap<String, String>();

    var registeredParts = convertToArray(registeredRoute);
    var requestParts = convertToArray(requestRoute);

    for (int i = 0; i < registeredParts.length; i++) {
      var registeredPart = registeredParts[i];
      var requestPart = requestParts[i];

      if (registeredPart.charAt(0) == ':') {
        var paramKey = registeredPart.substring(1);
        routeParams.put(paramKey, requestPart);
      }
    }

    return routeParams;
  }
}
