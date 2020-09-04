package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.JsonIO;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.ResponseInterface;

public class SimpleGetWithBody {
  public ResponseInterface get(RequestInterface request) {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setBody("Hello world");

    return response;
  }
}
