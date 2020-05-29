package com.k3ntako.HTTPServer;

import java.util.Iterator;
import java.util.Map;

public class Response {
  private RequestInterface request;

  public Response(RequestInterface request) {
    this.request = request;
  }

  public String createResponse() {
    return this.createHeader() + request.getBody();
  }

  private String createHeader() {
    var header = request.getProtocol();
    header = header + " 200 OK\n";


    Iterator headerIterator = request.getHeaders().entrySet().iterator();
    while (headerIterator.hasNext()) {
      Map.Entry mapElement = (Map.Entry) headerIterator.next();
      String key = (String) mapElement.getKey();
      String value = (String) mapElement.getValue();

      header = header + key + ": " + value + "\n";
    }

    header = header + "Connection: Closed\n\n";

    return header;
  }
}
