package com.k3ntako.HTTPServer;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteMatcher {

  public Route matchRoute(
      ArrayList<RegisteredRoute> registeredRoutes,
      RequestInterface request
  ) {
    Route exactMatch = null;

    for (RegisteredRoute registeredRoute : registeredRoutes) {
      var methodMatches = registeredRoute.method.equals(request.getMethod());
      var matches = methodMatches && registeredRoute.url.equals(request.getRoute());

      if (matches) {
        exactMatch = new Route(registeredRoute.url);
        exactMatch.setControllerMethod(registeredRoute.controller);
      }
    }

    if (exactMatch != null) {
      return exactMatch;
    }


    return getVariableRoute(registeredRoutes, request);
  }

  private Route getVariableRoute(ArrayList<RegisteredRoute> registeredRoutes, RequestInterface request) {
    var route = new Route(request.getRoute());
    var requestParts = convertToArray(request.getRoute());

    for (RegisteredRoute registeredRoute : registeredRoutes) {
      if (!registeredRoute.method.equals(request.getMethod())) {
        continue;
      }

      var registeredRouteParts = convertToArray(registeredRoute.url);

      var doPartsMatch = partsMatch(registeredRouteParts, requestParts);
      if (doPartsMatch) {
        route.setControllerMethod(registeredRoute.controller);
        route.setRouteParams(this.parseRouteParams(registeredRoute.url, request.getRoute()));
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
