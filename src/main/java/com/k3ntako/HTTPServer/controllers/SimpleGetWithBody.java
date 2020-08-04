package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

public class SimpleGetWithBody {
  public Response get(RequestInterface request) {
    var response = new Response();
    response.setBody("Hello world");

    return response;
  }
}
