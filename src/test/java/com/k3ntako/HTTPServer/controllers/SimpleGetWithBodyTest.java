package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGetWithBodyTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/simple_get_with_body", "HTTP/1.1", new HashMap<>(), "");

    var simpleGetWithBody = new SimpleGetWithBody();
    var response = simpleGetWithBody.getResponse(request);

    var responseStr = response.createResponse();
    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n\r\n" +
            "Hello world";
    assertEquals(expectedResponse, responseStr);
  }
}