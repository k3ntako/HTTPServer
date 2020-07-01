package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.ControllerInterface;

public class SimpleGet implements ControllerInterface {
  public Response getResponse(RequestInterface request) {
    return new Response();
  }
}
