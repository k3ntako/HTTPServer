package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseCreator;
import com.k3ntako.HTTPServer.RouteInterface;

public class Account implements RouteInterface {
  @Override
  public ResponseCreator getResponse(RequestInterface request) {
    var response = new ResponseCreator();
    response.setRedirect("http://127.0.0.1:5000/", 302);
    return response;
  }
}
