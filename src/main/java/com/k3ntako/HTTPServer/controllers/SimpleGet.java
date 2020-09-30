package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseInterface;

public class SimpleGet {
  public ResponseInterface get(RequestInterface request, ResponseInterface response) {
    return response;
  }
}
