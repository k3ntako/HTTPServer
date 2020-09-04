package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.JsonIO;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

public class Admin {
  public Response get(RequestInterface request) {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    response.setRedirect("http://127.0.0.1:5000/simple_get", 301);
    return response;
  }
}
