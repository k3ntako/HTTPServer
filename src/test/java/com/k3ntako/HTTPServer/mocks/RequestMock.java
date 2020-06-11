package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.RequestInterface;

import java.io.BufferedReader;
import java.util.HashMap;

public class RequestMock implements RequestInterface {
  private String method;
  private String route;
  private String protocol;
  private HashMap<String, String> headers;
  private String body;

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

  public void parseRequest(BufferedReader bufferedReader) {
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

  public String getBody() {
    return this.body;
  }
}