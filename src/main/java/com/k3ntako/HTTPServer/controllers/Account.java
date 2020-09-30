package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseInterface;

public class Account {
  public ResponseInterface get(RequestInterface request, ResponseInterface response) {
    response.setRedirect("http://127.0.0.1:5000/", 302);
    return response;
  }
}
