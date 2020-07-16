package com.k3ntako.HTTPServer;

import java.io.IOException;

public class RequestGenerator implements RequestGeneratorInterface {
  @Override
  public Request generateRequest(ServerIOInterface serverIO) throws IOException {
    var request = new Request(serverIO);
    request.parseRequest();
    return request;
  }
}
