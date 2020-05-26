package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class Request {
  private String method;
  private String route;
  private String protocol;
  private HashMap<String, String> headers;

  public void parseHeader(String header){
    var headerLines = header.split("\n");
    var requestLine = headerLines[0].split(" ");

    this.method = requestLine[0];
    this.route = requestLine[1];
    this.protocol = requestLine[2];

    this.headers = new HashMap<>();
    for(String line : headerLines) {
      var lineArr = line.split(": ");
      if(lineArr.length > 1) {
        var key = lineArr[0];
        var value = lineArr[1];
        this.headers.put(key, value);
      }
    }
  }

  public String getMethod() {
    return this.method;
  }

  public String getRoute() {
    return this.route;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public HashMap<String, String> getHeaders() {
    return this.headers;
  }
}
