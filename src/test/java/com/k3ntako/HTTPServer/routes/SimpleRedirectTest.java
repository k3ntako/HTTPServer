package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRedirectTest {

  @Test
  void getResponse() {
    var request = new RequestMock("GET", "/simple_redirect", "HTTP/1.1", new HashMap<>(), "");

    var simpleRedirect = new SimpleRedirect();
    var response = simpleRedirect.getResponse(request);

    var responseStr = response.createResponse();
    var expectedResponse = "HTTP/1.1 301 Moved Permanently\r\n" +
            "Location: http://127.0.0.1:5000/simple_get\r\n" +
            "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, responseStr);
  }
}