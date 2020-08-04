package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

public class NotFound {

  public Response handleNotFound(RequestInterface request) {
    var response = new Response();
    response.setStatus(404);
    return response;
  }
}
