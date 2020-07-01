package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class RouteRegistry {
  private HashMap<String, ControllerInterface> getRoutes = new HashMap<>();

  public void registerGet(String url, ControllerInterface route) {
    getRoutes.put(url, route);
  }

  public ControllerInterface get(String url){
    return getRoutes.get(url);
  }
}
