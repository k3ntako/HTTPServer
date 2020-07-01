package com.k3ntako.HTTPServer;


import java.util.HashMap;
import java.util.Map;

public class Response {
  private String body = "";
  private int status = 200;
  private HashMap<String, String> additionalHeaders = new HashMap<>();

  public Response() {
    this.statuses = new HashMap<>();

    this.statuses.put(200, "OK");
    this.statuses.put(301, "Moved Permanently");
    this.statuses.put(302, "Found");
    this.statuses.put(404, "Not Found");
  }

  private HashMap<Integer, String> statuses;

  public String createResponse() {
    return this.createHeader(body.length()) + this.body;
  }

  private String createHeader(int contentLength) {
    var statusMessage = statuses.get(status);

    var header = "HTTP/1.1 " + status + " " + statusMessage + "\r\n";

    var additionalHeaders = stringifyAdditionHeaders();
    header += additionalHeaders;

    header += "Content-Length: " + contentLength + "\r\n\r\n";

    return header;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void addHeader(String key, String value) {
    additionalHeaders.put(key, value);
  }

  public void setRedirect(String url, int status) {
    setStatus(status);
    addHeader("Location", url);
  }

  private String stringifyAdditionHeaders() {
    var stringBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();

      stringBuilder.append(key);
      stringBuilder.append(": ");
      stringBuilder.append(value);
      stringBuilder.append("\r\n");
    }

    return stringBuilder.toString();
  }
}
