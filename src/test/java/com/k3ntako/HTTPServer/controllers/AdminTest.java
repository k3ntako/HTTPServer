package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/admin", "HTTP/1.1", new HashMap<>(), "");

    var admin = new Admin();
    var response = (ResponseMock) admin.get(request, new ResponseMock());

    assertNull(response.setBodyArg);
    assertNull(response.setJsonBodyArg);
    assertEquals("http://127.0.0.1:5000/simple_get", response.setRedirectUrl);
    assertEquals(301, response.setRedirectStatus);
  }
}