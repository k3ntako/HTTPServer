package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.ServerIOMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

  @Test
  void parseRequest() throws IOException {
    var headerStr = "GET / HTTP/1.1\r\n" +
        "Host: localhost:5000\r\n" +
        "User-Agent: curl/7.64.1\r\n" +
        "Accept: */*\r\n\r\n";
    var serverIO = new ServerIOMock(headerStr);
    serverIO.init(new Socket());

    var request = new Request(serverIO);

    request.parseRequest();
    assertEquals("GET", request.getMethod());
    assertEquals("/", request.getRoute());
    assertEquals("HTTP/1.1", request.getProtocol());

    assertEquals("localhost:5000", request.getHeaders().get("Host"));
    assertEquals("curl/7.64.1", request.getHeaders().get("User-Agent"));
    assertEquals("*/*", request.getHeaders().get("Accept"));
  }

  @Test
  void parseBody() throws IOException {
    var header = "GET / HTTP/1.1\r\n" +
        "Content-Length: 68\r\n\r\n";
    var bodyStr = "Body line 1: abc\n" +
        "Body line 2: abc\n" +
        "Body line 3: abc\n" +
        "Body line 4: abc\n";

    var serverIO = new ServerIOMock(header + bodyStr);
    serverIO.init(new Socket());
    var request = new Request(serverIO);

    request.parseRequest();

    assertEquals(bodyStr, request.getBody());
  }
}
