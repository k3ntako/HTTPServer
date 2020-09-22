package com.k3ntako.HTTPServer.mocks;

import com.google.gson.JsonElement;
import com.k3ntako.HTTPServer.ResponseInterface;

import java.util.HashMap;

public class ResponseMock implements ResponseInterface {
  public boolean createResponseCalled = false;
  public String getSetBodyArg;
  public JsonElement getSetJsonBodyArg;
  public int getSetStatusArg;
  public String getSetRedirectUrl;
  public int getSetRedirectStatus;
  public HashMap<String, String> headers = new HashMap<>();

  @Override
  public String createResponse() {
    createResponseCalled = true;
    return null;
  }

  @Override
  public void setBody(String body) {
    getSetBodyArg = body;
  }

  @Override
  public void setJsonBody(JsonElement body) {
    getSetJsonBodyArg = body;
  }

  @Override
  public void setStatus(int status) {
    getSetStatusArg = status;
  }

  @Override
  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  @Override
  public void setRedirect(String url, int status) {
    getSetRedirectUrl = url;
    getSetRedirectStatus = status;
  }
}
