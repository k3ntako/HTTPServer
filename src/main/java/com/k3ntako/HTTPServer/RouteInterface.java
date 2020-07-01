package com.k3ntako.HTTPServer;

public interface RouteInterface {
  ResponseCreator getResponse(RequestInterface request);
}
