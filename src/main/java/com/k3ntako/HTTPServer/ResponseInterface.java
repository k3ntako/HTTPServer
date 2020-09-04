package com.k3ntako.HTTPServer;

public interface ResponseInterface {
  String createResponse() throws HTTPError;

  void setBody(String body);

  void setJsonBody(Object body);

  void setStatus(int status);

  void addHeader(String key, String value);

  void setRedirect(String url, int status);
}
