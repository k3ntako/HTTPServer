package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.IOGeneratorMock;
import com.k3ntako.HTTPServer.mocks.ServerSocketMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EchoServerTest {
  @Test
  void echoServerParsesHeader() {
    var clientInput = "GET / HTTP/1.1\n" +
            "Host: localhost:3000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "\r\n";

    var ioGeneratorMock = new IOGeneratorMock(clientInput);
    var request = new Request();
    var app = new EchoServer(ioGeneratorMock, request);
    var socket = new ServerSocketMock();
    app.run(socket);

    assertTrue(socket.acceptCalled);

    assertEquals("GET", request.getMethod());
    assertEquals("localhost:3000", request.getHeaders().get("Host"));
  }

  @Test
  void echoServerParsesBody() {
    var clientInput = "GET / HTTP/1.1\n" +
            "Host: localhost:3000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "Content-Length: 68\n" +
            "\r\n";

    var bodyStr = "Body line 1: abc\n" +
            "Body line 2: abc\n" +
            "Body line 3: abc\n" +
            "Body line 4: abc\n";

    var ioGeneratorMock = new IOGeneratorMock(clientInput.concat(bodyStr));
    var request = new Request();
    var app = new EchoServer(ioGeneratorMock, request);
    var socket = new ServerSocketMock();
    app.run(socket);

    assertEquals(bodyStr, request.getBody());
  }
}