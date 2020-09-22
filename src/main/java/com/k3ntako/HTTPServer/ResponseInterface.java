package com.k3ntako.HTTPServer;

import com.google.gson.JsonElement;

public interface ResponseInterface {
  String createResponse() throws HTTPError;

  void setBody(String body);

  void setJsonBody(JsonElement body);

  void setStatus(int status);

  void addHeader(String key, String value);

  void setRedirect(String url, int status);
}
