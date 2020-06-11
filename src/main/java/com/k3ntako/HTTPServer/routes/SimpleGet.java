package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.RouteInterface;

public class SimpleGet implements RouteInterface {
  public Response getResponse(RequestInterface request) {
    return new Response();
  }
}
