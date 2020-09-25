package com.k3ntako.HTTPServer;

public interface ErrorHandlerInterface {
  ResponseInterface handleError(HTTPError e) throws HTTPError;

  ResponseInterface handleError(Exception e) throws HTTPError;
}
