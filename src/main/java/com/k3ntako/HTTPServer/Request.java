package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.utilities.MimeTypesInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Request implements RequestInterface {
  private String method;
  private String route;
  private String protocol;
  final private HashMap<String, String> headers;
  private byte[] body;
  final private ClientSocketIOInterface clientSocketIO;
  private HashMap<String, String> routeParams;
  private MimeTypesInterface mimeTypes;

  public Request(ClientSocketIOInterface clientSocketIO, MimeTypesInterface mimeTypes) {
    this.clientSocketIO = clientSocketIO;
    this.headers = new HashMap<>();
    this.mimeTypes = mimeTypes;
  }

  public void parseRequest() throws IOException, HTTPError {
    this.parseHeader();

    var contentLength = 0;
    if (this.headers.containsKey("Content-Length")) {
      contentLength = Integer.parseInt(this.headers.get("Content-Length"));
    }

    if (contentLength > 0) {
      this.parseBody(contentLength);
    }

    verifyContentType(headers.get("Content-Type"));
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

  private void verifyContentType(String headerContentType) throws HTTPError {
    var bodyContentType = mimeTypes.guessContentTypeFromBytes(this.body);
    if(bodyContentType != null && !headerContentType.toLowerCase().equals(bodyContentType.toLowerCase())){
      throw new HTTPError(400, "Bad Request");
    }
  }

  private String readLine() throws IOException {
    var line = clientSocketIO.readLine();


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
    this.body = clientSocketIO.parseBody(contentLength);
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

  public byte[] getBody() {
    if (Objects.isNull(this.body)) {
      return new byte[0];
    } else {
      return this.body;
    }
  }
}
