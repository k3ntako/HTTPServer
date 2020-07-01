package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseCreatorTest {

  @Test
  void createEmptyResponse() {
    var response = new ResponseCreator();

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 0\r\n\r\n";
    assertEquals(expected, headerStr);
  }

  @Test
  void createResponseWithBody() {
    var response = new ResponseCreator();
    response.setBody("This\nis\nthe\nresponse\nbody!!");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 27\r\n\r\n" +
            "This\nis\nthe\nresponse\nbody!!";
    assertEquals(expected, headerStr);
  }

  @Test
  void setStatus() {
    var response = new ResponseCreator();
    response.setStatus(404);

    var headerStr = response.createResponse();
    var expected = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }

  @Test
  void addHeader() {
    var response = new ResponseCreator();
    response.setStatus(301);
    response.addHeader("Location", "/simple_get");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 301 Moved Permanently\r\n" +
            "Location: /simple_get\r\n" +
            "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }

  @Test
  void setRedirect() {
    var response = new ResponseCreator();
    response.setRedirect("/test", 302);

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 302 Found\r\n" +
            "Location: /test\r\n" +
            "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }
}
