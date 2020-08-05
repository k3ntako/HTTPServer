package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class Route {
  private String path;
  private ControllerMethodInterface controllerMethod;
  private HashMap<String, String> params = new HashMap<>();

  public Route(String path) {
    this.path = path;
  }

  public String getRoutePath() {
    return path;
  }

  public void setControllerMethod(ControllerMethodInterface controllerMethod) {
    this.controllerMethod = controllerMethod;
  }

  public ControllerMethodInterface getControllerMethod() {
    return this.controllerMethod;
  }

  public void setParams(HashMap<String, String> params) {
    this.params = params;
  }

  public HashMap<String, String> getParams() {
    return this.params;
  }
}
