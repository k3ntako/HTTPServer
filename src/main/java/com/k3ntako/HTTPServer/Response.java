package com.k3ntako.HTTPServer;


public class Response {
  private RequestInterface request;
  private String body = "";
  public Response(RequestInterface request) {
    this.request = request;
  }

  public String createResponse() {
    return this.createHeader(body.length()) + this.body;
  }

  private String createHeader(int contentLength) {
    var header = "HTTP/1.1 200 OK\r\n";

    if(contentLength > 0) {
      header = header + "Content-Length: " + contentLength + "\r\n";
    }

    return header + "\r\n";
  }

  public void setBody(String body){
    this.body = body;
  }
}
