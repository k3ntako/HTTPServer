package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

  @Test
  void createEmptyResponse() {
    var response = new Response();

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 0\r\n\r\n";
    assertEquals(expected, headerStr);
  }

  @Test
  void createResponseWithBody() {
    var response = new Response();
    response.setBody("This\nis\nthe\nresponse\nbody!!");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 27\r\n\r\n" +
            "This\nis\nthe\nresponse\nbody!!";
    assertEquals(expected, headerStr);
  }

  @Test
  void setStatus() {
    var response = new Response();
    response.setStatus(404);

    var headerStr = response.createResponse();
    var expected = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }

  @Test
  void addHeader() {
    var response = new Response();
    response.setStatus(301);
    response.addHeader("Location", "/simple_get");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 301 Moved Permanently\r\n" +
            "Location: /simple_get\r\n" +
            "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }
}
