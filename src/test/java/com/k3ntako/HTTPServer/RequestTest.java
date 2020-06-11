package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

  @Test
  void parseRequest() {
    var headerStr = "GET / HTTP/1.1\r\n" +
            "Host: localhost:5000\r\n" +
            "User-Agent: curl/7.64.1\r\n" +
            "Accept: */*\r\n\r\n";
    var bufferedReader = new BufferedReader(new StringReader(headerStr));

    var requestParser = new Request();

    requestParser.parseRequest(bufferedReader);
    assertEquals("GET", requestParser.getMethod());
    assertEquals("/", requestParser.getRoute());
    assertEquals("HTTP/1.1", requestParser.getProtocol());

    assertEquals("localhost:5000", requestParser.getHeaders().get("Host"));
    assertEquals("curl/7.64.1", requestParser.getHeaders().get("User-Agent"));
    assertEquals("*/*", requestParser.getHeaders().get("Accept"));
  }

  @Test
  void parseBody() {
    var header = "GET / HTTP/1.1\r\n" +
            "Content-Length: 68\r\n\r\n";
    var bodyStr = "Body line 1: abc\n" +
            "Body line 2: abc\n" +
            "Body line 3: abc\n" +
            "Body line 4: abc\n";

    var bufferedReader = new BufferedReader(new StringReader(header + bodyStr));
    var request = new Request();

    request.parseRequest(bufferedReader);

    assertEquals(bodyStr, request.getBody());
  }
}
