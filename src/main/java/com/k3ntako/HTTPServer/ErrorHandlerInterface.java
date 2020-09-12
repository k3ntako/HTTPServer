package com.k3ntako.HTTPServer;

public interface ErrorHandlerInterface {
  ResponseInterface handleError(HTTPError e);

  ResponseInterface handleError(Exception e);
}
