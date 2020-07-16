package com.k3ntako.HTTPServer;

public interface ErrorHandlerInterface {
  Response handleError(HTTPError e);

  Response handleError(Exception e);
}
