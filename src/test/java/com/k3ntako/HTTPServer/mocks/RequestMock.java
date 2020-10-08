package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.RequestInterface;

import java.util.HashMap;

public class RequestMock implements RequestInterface {
  final private String method;
  final private String route;
  private String protocol = "HTTP/1.1";
  private HashMap<String, String> headers = new HashMap<>();
  private HashMap<String, String> routeParams;
  private Object body = "";

  public RequestMock(
      String method,
      String route
  ) {
    this.method = method;
    this.route = route;
  }

  public RequestMock(
      String method,
      String route,
      String body
  ) {
    this.method = method;
    this.route = route;
    this.body = body;
  }

  public RequestMock(
      String method,
      String route,
      byte[] body
  ) {
    this.method = method;
    this.route = route;
    this.body = body;
  }

  public RequestMock(
      String method,
      String route,
      String protocol,
      HashMap<String, String> headers,
      String body
  ) {
    this.method = method;
    this.route = route;
    this.protocol = protocol;
    this.headers = headers;
    this.body = body;
  }

  public RequestMock(
      String method,
      String route,
      String protocol,
      HashMap<String, String> headers,
      byte[] body
  ) {
    this.method = method;
    this.route = route;
    this.protocol = protocol;
    this.headers = headers;
    this.body = body;
  }

  public void parseRequest() {
  }

  public void setRouteParams(HashMap<String, String> routeParams) {
    this.routeParams = routeParams;
  }

  @Override
  public HashMap<String, String> getRouteParams() {
    return routeParams;
  }

  @Override
  public String getRouteParam(String key) {
    return this.routeParams.get(key);
  }

  public String getMethod() {
    return this.method;
  }

  public String getRoute() {
    return this.route;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public HashMap<String, String> getHeaders() {
    return this.headers;
  }

  public Object getBody() {
    return this.body;
  }
}
