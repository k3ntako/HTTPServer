package com.k3ntako.HTTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Request implements RequestInterface {
  private String method;
  private String route;
  private String protocol;
  private HashMap<String, String> headers;
  private String body;

  public Request() {
    this.headers = new HashMap<>();
  }

  public void parseRequest(BufferedReader bufferedReader) {
    try {
      this.parseHeader(bufferedReader);

      var contentLength = 0;
      if (this.headers.containsKey("Content-Length")) {
        contentLength = Integer.parseInt(this.headers.get("Content-Length"));
      }

      if (contentLength > 0) {
        this.parseBody(bufferedReader, contentLength);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void parseHeader(BufferedReader bufferedReader) throws IOException {
    String line;
    while ((line = this.readLine(bufferedReader)) != null) {
      if (line.contains(":")) {
        this.parseHeaderAttributes(line);
      } else if (line.length() > 0) {
        this.parseRequestLine(line);
      }
    }
  }

  private String readLine(BufferedReader bufferedReader) throws IOException {
    var line = bufferedReader.readLine();

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

  private void parseBody(BufferedReader bufferedReader, int contentLength) {
    try {
      var bodyStr = "";
      char character;

      while (bodyStr.length() < contentLength) {
        character = (char) (bufferedReader.read());
        bodyStr = bodyStr.concat(String.valueOf(character));
      }

      this.body = bodyStr;
    } catch (IOException e) {
      e.printStackTrace();
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

  public String getBody() {
    if (Objects.isNull(this.body)) {
      return "";
    } else {
      return this.body;
    }
  }
}
