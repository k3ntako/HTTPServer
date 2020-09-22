package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseInterface;

public class Admin {
  public ResponseInterface get(RequestInterface request, ResponseInterface response) {
    response.setRedirect("http://127.0.0.1:5000/simple_get", 301);
    return response;
  }
}
