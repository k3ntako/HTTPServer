package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGetTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/simple_get", "HTTP/1.1", new HashMap<>(), "");

    var simpleGet = new SimpleGet();
    var response = (ResponseMock) simpleGet.get(request, new ResponseMock());

    assertNull(response.setBodyArg);
    assertNull(response.setJsonBodyArg);
  }
}