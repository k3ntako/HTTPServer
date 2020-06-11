package com.k3ntako.HTTPServer;

import java.io.BufferedReader;

public class RequestHandler implements RequestHandlerInterface {
  @Override
  public Request handleRequest(BufferedReader bufferedReader){
    var request = new Request();
    request.parseRequest(bufferedReader);
    return request;
  }
}
