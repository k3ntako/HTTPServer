package com.k3ntako.HTTPServer;

public interface RequestHandlerInterface {
  RequestInterface handleRequest(ServerIOInterface serverIO);
}
