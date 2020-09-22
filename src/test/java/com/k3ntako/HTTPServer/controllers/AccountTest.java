package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/account", "HTTP/1.1", new HashMap<>(), "");

    var account = new Account();
    var response = (ResponseMock) account.get(request, new ResponseMock());

    assertNull(response.setBodyArg);
    assertNull(response.setJsonBodyArg);

    assertEquals("http://127.0.0.1:5000/", response.setRedirectUrl);
    assertEquals(302, response.setRedirectStatus);
  }
}