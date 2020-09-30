package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGetWithBodyTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/simple_get_with_body", "HTTP/1.1", new HashMap<>(), "");

    var simpleGetWithBody = new SimpleGetWithBody();
    var response = (ResponseMock) simpleGetWithBody.get(request, new ResponseMock());

    assertNull(response.getSetJsonBodyArg);
    assertEquals("Hello world", response.getSetBodyArg);
  }
}