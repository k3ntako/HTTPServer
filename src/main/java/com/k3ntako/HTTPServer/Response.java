package com.k3ntako.HTTPServer;


import java.util.HashMap;

public class Response {
  private String body = "";
  private int status = 200;

  public Response() {
    this.statuses = new HashMap<>();

    this.statuses.put(200, "OK");
    this.statuses.put(404, "Not Found");
  }

  private HashMap<Integer, String> statuses;

  public String createResponse() {
    return this.createHeader(body.length()) + this.body;
  }

  private String createHeader(int contentLength) {
    var statusMessage = statuses.get(status);

    var header = "HTTP/1.1 " + status + " " + statusMessage + "\r\n";
    header = header + "Content-Length: " + contentLength + "\r\n\r\n";

    return header;
  }

  public void setBody(String body){
    this.body = body;
  }

  public void setStatus(int status){
    this.status = status;
  }
}
