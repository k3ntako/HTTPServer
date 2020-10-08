package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.utilities.MimeTypes;

import java.io.IOException;

public class RequestGenerator implements RequestGeneratorInterface {
  @Override
  public Request generateRequest(ClientSocketIOInterface clientSocketIO) throws IOException, HTTPError {
    var request = new Request(clientSocketIO, new MimeTypes());
    request.parseRequest();
    return request;
  }
}
