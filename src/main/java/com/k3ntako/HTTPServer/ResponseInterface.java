package com.k3ntako.HTTPServer;

import com.google.gson.JsonElement;

import java.io.IOException;

public interface ResponseInterface {
  byte[] createResponse() throws HTTPError;

  void setBody(String body) throws HTTPError;

  void setBody(JsonElement body) throws HTTPError;

  void setBody(byte[] body) throws HTTPError;

  void setBody(byte[] body, String contentType) throws HTTPError;

  void setStatus(int status);

  void addHeader(String key, String value);

  void setRedirect(String url, int status);
}
