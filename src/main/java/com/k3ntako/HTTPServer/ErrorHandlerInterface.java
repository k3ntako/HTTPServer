package com.k3ntako.HTTPServer;

public interface ErrorHandlerInterface {
  Response handleError(Exception e);
}
