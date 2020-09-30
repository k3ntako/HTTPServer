package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseInterface;

public class NotFound {

  public ResponseInterface handleNotFound(RequestInterface request, ResponseInterface response) {
    response.setStatus(404);
    return response;
  }
}
