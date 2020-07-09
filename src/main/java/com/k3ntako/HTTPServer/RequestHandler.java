package com.k3ntako.HTTPServer;

import java.io.IOException;

public class RequestHandler implements RequestHandlerInterface {
  @Override
  public Request handleRequest(ServerIOInterface serverIO) throws IOException {
    var request = new Request(serverIO);
    request.parseRequest();
    return request;
  }
}
