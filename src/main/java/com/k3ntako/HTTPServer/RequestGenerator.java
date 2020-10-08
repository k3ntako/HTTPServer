package com.k3ntako.HTTPServer;

import java.io.IOException;

public class RequestGenerator implements RequestGeneratorInterface {
  @Override
  public Request generateRequest(ClientSocketIOInterface clientSocketIO) throws IOException, HTTPError {
    var request = new Request(clientSocketIO);
    request.parseRequest();
    return request;
  }
}
