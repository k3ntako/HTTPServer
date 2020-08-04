package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

public class SimpleGet {
  public Response get(RequestInterface request) {
    return new Response();
  }
}
