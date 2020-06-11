package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGetTest {

  @Test
  void getResonse() {
    var request = new RequestMock("GET", "/simple-get", "HTTP/1.1", new HashMap<>(), "");

    var simpleGet = new SimpleGet();
    var response = simpleGet.getResponse(request);

    var responseStr = response.createResponse();
    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n\r\n" +
            "Hello world";
    assertEquals(expectedResponse, responseStr);
  }
}