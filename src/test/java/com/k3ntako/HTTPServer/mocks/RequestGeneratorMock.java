package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ClientSocketIOInterface;
import com.k3ntako.HTTPServer.RequestGeneratorInterface;
import com.k3ntako.HTTPServer.RequestInterface;

import java.util.HashMap;

public class RequestGeneratorMock implements RequestGeneratorInterface {
  final private String method;
  final private String route;
  final private String protocol;
  final private HashMap<String, String> headers;
  final private String body;
  private boolean handleRequestCalled = false;

  public RequestGeneratorMock() {
    this.method = "GET";
    this.route = "/api/simple_get_with_body";
    this.protocol = "HTTP/1.1";
    this.headers = new HashMap<>();
    this.body = "";
  }

  public RequestInterface generateRequest(ClientSocketIOInterface clientSocketIO) {
    var request = new RequestMock(method, route, protocol, headers, body);
    handleRequestCalled = true;
    return request;
  }

  public boolean wasHandleRequestCalled() {
    return handleRequestCalled;
  }
}
