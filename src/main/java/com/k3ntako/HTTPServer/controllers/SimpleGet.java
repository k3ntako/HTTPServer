package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.JsonIO;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;
import com.k3ntako.HTTPServer.ResponseInterface;

public class SimpleGet {
  public ResponseInterface get(RequestInterface request) {
    var jsonIO = new JsonIO(new Gson());
    return new Response(jsonIO);
  }
}
