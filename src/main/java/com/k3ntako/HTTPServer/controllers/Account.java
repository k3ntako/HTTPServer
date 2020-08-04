package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

public class Account {
  public Response get(RequestInterface request) {
    var response = new Response();
    response.setRedirect("http://127.0.0.1:5000/", 302);
    return response;
  }
}
