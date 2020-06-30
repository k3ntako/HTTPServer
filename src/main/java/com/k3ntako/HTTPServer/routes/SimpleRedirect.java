package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.RouteInterface;

public class SimpleRedirect implements RouteInterface {
  @Override
  public Response getResponse(RequestInterface request) {
    var response = new Response();
    response.setStatus(301);
    response.addHeader("Location", "http://127.0.0.1:5000/simple_get");
    return response;
  }
}
