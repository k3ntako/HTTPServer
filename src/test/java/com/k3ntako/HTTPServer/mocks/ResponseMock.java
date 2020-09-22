package com.k3ntako.HTTPServer.mocks;

import com.google.gson.JsonElement;
import com.k3ntako.HTTPServer.ResponseInterface;

import java.util.HashMap;

public class ResponseMock implements ResponseInterface {
  public boolean createResponseCalled = false;
  public String setBodyArg;
  public JsonElement setJsonBodyArg;
  public int setStatusArg;
  public String setRedirectUrl;
  public int setRedirectStatus;
  public HashMap<String, String> headers = new HashMap<>();

  @Override
  public String createResponse() {
    createResponseCalled = true;
    return null;
  }

  @Override
  public void setBody(String body) {
    setBodyArg = body;
  }

  @Override
  public void setJsonBody(JsonElement body) {
    setJsonBodyArg = body;
  }

  @Override
  public void setStatus(int status) {
    setStatusArg = status;
  }

  @Override
  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  @Override
  public void setRedirect(String url, int status) {
    setRedirectUrl = url;
    setRedirectStatus = status;
  }
}
