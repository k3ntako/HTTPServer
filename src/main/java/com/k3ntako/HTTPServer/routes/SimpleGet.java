package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseCreator;
import com.k3ntako.HTTPServer.RouteInterface;

public class SimpleGet implements RouteInterface {
  public ResponseCreator getResponse(RequestInterface request) {
    return new ResponseCreator();
  }
}
