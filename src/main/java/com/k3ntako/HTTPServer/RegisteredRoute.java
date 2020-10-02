package com.k3ntako.HTTPServer;

public class RegisteredRoute {
  public ControllerMethodInterface controller;
  public String method;
  public String url;

  public RegisteredRoute(String method, String url, ControllerMethodInterface controller) {
    this.controller = controller;
    this.method = method;
    this.url = url;
  }
}
