package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

  @Test
  void createEmptyResponse() {
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Length", "17");
    headers.put("Content-Type", "text/html; charset=UTF-8");

    var response = new Response();

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 0\r\n\r\n";
    assertEquals(expected, headerStr);
  }

  @Test
  void createResponseWithBody() {
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Length", "17");
    headers.put("Content-Type", "text/html; charset=UTF-8");

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
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Length", "17");
    headers.put("Content-Type", "text/html; charset=UTF-8");

    var response = new Response();
    response.setStatus(404);

    var headerStr = response.createResponse();
    var expected = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }
}
