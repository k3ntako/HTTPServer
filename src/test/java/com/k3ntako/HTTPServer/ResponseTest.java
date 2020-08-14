package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

  @Test
  void createEmptyResponse() throws HTTPError {
    var response = new Response();

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertEquals(expected, headerStr);
  }

  @Test
  void createResponseWithBody() throws HTTPError {
    var response = new Response();
    response.setBody("This\nis\nthe\nresponse\nbody!!");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 27\r\n\r\n" +
        "This\nis\nthe\nresponse\nbody!!";
    assertEquals(expected, headerStr);
  }

  @Test
  void setStatus() throws HTTPError {
    var response = new Response();
    response.setStatus(404);

    var headerStr = response.createResponse();
    var expected = "HTTP/1.1 404 Not Found\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }

  @Test
  void addHeader() throws HTTPError {
    var response = new Response();
    response.setStatus(301);
    response.addHeader("Location", "/simple_get");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 301 Moved Permanently\r\n" +
        "Location: /simple_get\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }

  @Test
  void setRedirect() throws HTTPError {
    var response = new Response();
    response.setRedirect("/test", 302);

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 302 Found\r\n" +
        "Location: /test\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expected, headerStr);
  }

  @Test
  void throwErrorIfBodyIsNull() {
    var response = new Response();
    response.setBody(null);

    HTTPError exception = assertThrows(
        HTTPError.class, response::createResponse
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }
}
