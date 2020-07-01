package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.ControllerInterface;

public class SimpleGetWithBody implements ControllerInterface {
  public Response getResponse(RequestInterface request) {
    var response = new Response();
    response.setBody("Hello world");

    return response;
  }
}
