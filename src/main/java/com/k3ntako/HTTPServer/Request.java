package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Request implements RequestInterface {
  private String method;
  private String route;
  private String protocol;
  private HashMap<String, String> headers;
  private String body;
  private ServerIOInterface serverIO;
  private HashMap<String, String> routeParams;

  public Request(ServerIOInterface serverIO) {
    this.serverIO = serverIO;
    this.headers = new HashMap<>();
  }

  public void parseRequest() throws IOException {
    this.parseHeader();

    var contentLength = 0;
    if (this.headers.containsKey("Content-Length")) {
      contentLength = Integer.parseInt(this.headers.get("Content-Length"));
    }

    if (contentLength > 0) {
      this.parseBody(contentLength);
    }
  }

  private void parseHeader() throws IOException {
    String line;
    while ((line = this.readLine()) != null) {
      if (line.contains(":")) {
        this.parseHeaderAttributes(line);
      } else if (line.length() > 0) {
        this.parseRequestLine(line);
      }
    }
  }

  private String readLine() throws IOException {
    var line = serverIO.readLine();

    if (line == null || line.length() == 0) {
      return null;
    }

    return line;
  }

  private void parseRequestLine(String line) {
    var requestLine = line.split(" ");

    this.method = requestLine[0];
    this.route = requestLine[1];
    this.protocol = requestLine[2];
  }

  private void parseHeaderAttributes(String line) {
    var lineArr = line.split(": ");

    if (lineArr.length > 1) {
      var key = lineArr[0];
      var value = lineArr[1];
      this.headers.put(key, value);
    }
  }

  private void parseBody(int contentLength) throws IOException {
    var bodyStr = "";
    char character;

    while (bodyStr.length() < contentLength) {
      character = (serverIO.read());
      bodyStr = bodyStr.concat(String.valueOf(character));
    }

    this.body = bodyStr;
  }

  public void setRouteParams(HashMap<String, String> routeParams) {
    this.routeParams = routeParams;
  }

  public HashMap<String, String> getRouteParams() {
    return this.routeParams;
  }

  public String getRouteParam(String key) {
    return this.routeParams.get(key);
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

  public String getBody() {
    if (Objects.isNull(this.body)) {
      return "";
    } else {
      return this.body;
    }
  }
}
