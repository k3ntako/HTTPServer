package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ResponseInterface;

public class SimpleGetWithBody {
  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws HTTPError {
    response.setBody("Hello world");

    return response;
  }
}
