package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface RequestHandlerInterface {
  RequestInterface handleRequest(ServerIOInterface serverIO) throws IOException, HTTPError;
}
