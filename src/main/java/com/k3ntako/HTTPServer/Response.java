package com.k3ntako.HTTPServer;


import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response.Status;

public class Response {
  private String body = "";
  private int status = 200;
  private HashMap<String, String> additionalHeaders = new HashMap<>();

  public String createResponse() throws HTTPError {
    if (body == null) {
      throw new HTTPError(500, "Response body cannot be null");
    }

    return this.createHeader(body.length()) + this.body;
  }

  private String createHeader(int contentLength) {
    var status = this.status;

    if (body.length() == 0 && status >= 200 && status <= 299) {
      status = 204;
    }

    var header = "HTTP/1.1 " + status + " " + Status.fromStatusCode(status) + "\r\n";

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
