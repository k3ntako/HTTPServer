package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class RouteRegistry {
  private HashMap<String, RouteInterface> getRoutes = new HashMap<>();

  public void registerGet(String url, RouteInterface route) {
    getRoutes.put(url, route);
  }

  public RouteInterface get(String url){
    return getRoutes.get(url);
  }
}
