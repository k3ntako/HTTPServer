package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.Request;
import com.k3ntako.HTTPServer.RequestHandlerInterface;
import com.k3ntako.HTTPServer.RequestInterface;

import java.io.BufferedReader;
import java.util.HashMap;

public class RequestHandlerMock implements RequestHandlerInterface  {
  private String method;
  private String route;
  private String protocol;
  private HashMap<String, String> headers;
  private String body;
  private RequestInterface lastRequest;
  private boolean handleRequestCalled = false;

  public RequestHandlerMock(
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

  public RequestHandlerMock() {
    this.method = "GET";
    this.route = "/simple-get";
    this.protocol = "HTTP/1.1";
    this.headers = new HashMap<>();
    this.body = "";
  }

  public RequestInterface handleRequest(BufferedReader bufferedReader){
    var request = new RequestMock(method, route, protocol, headers, body);
    lastRequest = request;
    handleRequestCalled = true;
    return request;
  }

  public RequestInterface getLastRequest() {
    return lastRequest;
  }
  public boolean wasHandleRequestCalled() {
    return handleRequestCalled;
  }
}
