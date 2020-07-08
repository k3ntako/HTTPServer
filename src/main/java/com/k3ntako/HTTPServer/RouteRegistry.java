package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class RouteRegistry {
  private HashMap<String, ControllerInterface> getRoutes = new HashMap<>();
  private HashMap<String, ControllerInterface> postRoutes = new HashMap<>();

  public void registerGet(String url, ControllerInterface controller) {
    getRoutes.put(url, controller);
  }

  public void registerPost(String url, ControllerInterface controller) {
    postRoutes.put(url, controller);
  }

  public ControllerInterface get(String url) {
    return getRoutes.get(url);
  }

  public ControllerInterface post(String url) {
    return postRoutes.get(url);
  }
}
