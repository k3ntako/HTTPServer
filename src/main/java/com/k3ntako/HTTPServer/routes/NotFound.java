package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseCreator;
import com.k3ntako.HTTPServer.RouteInterface;

public class NotFound implements RouteInterface {

  @Override
  public ResponseCreator getResponse(RequestInterface request) {
    var response = new ResponseCreator();
    response.setStatus(404);
    return response;
  }
}
