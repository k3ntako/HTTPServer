package com.k3ntako.HTTPServer;

public class RequestHandler implements RequestHandlerInterface {
  @Override
  public Request handleRequest(ServerIOInterface serverIO){
    var request = new Request(serverIO);
    request.parseRequest();
    return request;
  }
}
