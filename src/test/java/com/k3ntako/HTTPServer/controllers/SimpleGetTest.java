package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGetTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/simple_get", "HTTP/1.1", new HashMap<>(), "");

    var simpleGet = new SimpleGet();
    var response = simpleGet.get(request);

    var responseStr = response.createResponse();
    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, responseStr);
  }
}