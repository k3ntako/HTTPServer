package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ResponseInterface;

public class ResponseMock implements ResponseInterface {
  public boolean createResponseCalled = false;
  public String setBodyArg;
  public Object setJsonBodyArg;
  public int setStatusArg;
  public String addHeaderKey;
  public String addHeaderValue;
  public String setRedirectUrl;
  public int setRedirectStatus;

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
  public void setJsonBody(Object body) {
    setJsonBodyArg = body;
  }

  @Override
  public void setStatus(int status) {
    setStatusArg = status;
  }

  @Override
  public void addHeader(String key, String value) {
    addHeaderKey = key;
    addHeaderValue = value;
  }

  @Override
  public void setRedirect(String url, int status) {
    setRedirectUrl = url;
    setRedirectStatus = status;
  }
}
