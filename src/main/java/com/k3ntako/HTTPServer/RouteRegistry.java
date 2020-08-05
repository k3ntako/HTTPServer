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

  public HashMap<String, Object> getController(String method, String url) {
    var routesForMethod = routes.get(method);

    if (routesForMethod == null) {
      return null;
    }

    var controllerMethod = routesForMethod.get(url);
    if (controllerMethod != null) {
      HashMap<String, Object> returnHash = new HashMap<>();
      returnHash.put("controllerMethod", controllerMethod);
      returnHash.put("params", new HashMap<String, String>());
      return returnHash;
    }

    return getVariableRoute(routesForMethod, url);
  }

  private HashMap<String, Object> getVariableRoute(
      HashMap<String, ControllerMethodInterface> routesForMethod, String url
  ) {
    var requestParts = removeBlanks(url.split("/"));

    for (String registeredRoute : routesForMethod.keySet()) {
      var registeredParts = removeBlanks(registeredRoute.split("/"));

      if (registeredParts.length != requestParts.length) {
        continue;
      }

      var partsMatchHash = partsMatch(registeredParts, requestParts);
      final var doPartsMatch = (boolean) partsMatchHash.get("doPartsMatch");
      final HashMap<String, String> params = (HashMap<String, String>) partsMatchHash.get("params");

      if (doPartsMatch) {
        var returnHash = new HashMap<String, Object>();
        returnHash.put("controllerMethod", routesForMethod.get(registeredRoute));
        returnHash.put("params", params);
        return returnHash;
      }
    }

    return null;
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

  private HashMap<String, Object> partsMatch(String[] registeredParts, String[] requestParts) {
    var returnHash = new HashMap<String, Object>();
    returnHash.put("doPartsMatch", false);

    var params = new HashMap<String, String>();

    for (int i = 0; i < registeredParts.length; i++) {
      var registeredPart = registeredParts[i];
      var requestPart = requestParts[i];

      if (registeredPart.charAt(0) == ':') {
        var paramKey = registeredPart.substring(1);
        params.put(paramKey, requestPart);
        continue;
      }

      if (!registeredPart.equals(requestPart)) {
        return returnHash;
      }
    }

    returnHash.put("doPartsMatch", true);
    returnHash.put("params", params);
    return returnHash;
  }
}
