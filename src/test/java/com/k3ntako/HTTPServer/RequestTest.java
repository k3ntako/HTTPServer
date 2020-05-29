package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

  @Test
  void parseRequest() {
    var headerStr = "GET / HTTP/1.1\n" +
            "Host: localhost:3000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "\r\n";

    var bufferedReader = new BufferedReader(new StringReader(headerStr));

    var requestParser = new Request();

    requestParser.parseRequest(bufferedReader);
    assertEquals("GET", requestParser.getMethod());
    assertEquals("/", requestParser.getRoute());
    assertEquals("HTTP/1.1", requestParser.getProtocol());

    assertEquals("localhost:3000", requestParser.getHeaders().get("Host"));
    assertEquals("curl/7.64.1", requestParser.getHeaders().get("User-Agent"));
    assertEquals("*/*", requestParser.getHeaders().get("Accept"));
  }

  @Test
  void parseBody() {
    var bodyStr = "Body line 1: abc\n" +
            "Body line 2: abc\n" +
            "Body line 3: abc\n" +
            "Body line 4: abc\n";

    var bufferedReader = new BufferedReader(new StringReader(bodyStr));
    var request = new Request();

    request.parseBody(bufferedReader, 68);

    assertEquals(bodyStr, request.getBody());
  }
}
