package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.ControllerInterface;

public class NotFound implements ControllerInterface {

  @Override
  public Response getResponse(RequestInterface request) {
    var response = new Response();
    response.setStatus(404);
    return response;
  }
}
