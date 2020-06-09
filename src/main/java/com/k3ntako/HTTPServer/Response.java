package com.k3ntako.HTTPServer;


public class Response {
  private RequestInterface request;

  public Response(RequestInterface request) {
    this.request = request;
  }

  public String createResponse() {
    var body = request.getBody();
    return this.createHeader(body.length()) + body;
  }

  private String createHeader(int contentLength) {
    var header = request.getProtocol();
    header =  "HTTP/1.1 200 OK\r\n";

    if(contentLength > 0) {
      header = header + "Content-Length: " + contentLength + "\r\n";
    }

    header = header + "\r\n";

    return header;
  }
}
