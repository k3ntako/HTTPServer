package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/account", "HTTP/1.1", new HashMap<>(), "");

    var account = new Account();
    var response = account.get(request);

    var responseStr = response.createResponse();
    var expectedResponse = "HTTP/1.1 302 Found\r\n" +
        "Location: http://127.0.0.1:5000/\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, responseStr);
  }
}