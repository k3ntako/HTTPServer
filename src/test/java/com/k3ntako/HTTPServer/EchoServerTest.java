package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.IOGeneratorMock;
import com.k3ntako.HTTPServer.mocks.ServerSocketMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EchoServerTest {
  @Test
  void echoServerParsesHeader() {
    var clientInput = "GET / HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "\n\n";

    var ioGeneratorMock = new IOGeneratorMock(clientInput);
    var request = new Request();
    var socket = new ServerSocketMock();
    var app = new EchoServer(ioGeneratorMock, request, socket);
    app.run();

    assertTrue(socket.acceptCalled);

    assertEquals("GET", request.getMethod());
    assertEquals("localhost:5000", request.getHeaders().get("Host"));

    app.stop();
  }

  @Test
  void echoServerParsesBody() {
    var clientInput = "GET / HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "Content-Length: 68\n" +
            "\n\n";

    var bodyStr = "Body line 1: abc\n" +
            "Body line 2: abc\n" +
            "Body line 3: abc\n" +
            "Body line 4: abc";

    var ioGeneratorMock = new IOGeneratorMock(clientInput.concat(bodyStr));
    var request = new Request();
    var socket = new ServerSocketMock();
    var app = new EchoServer(ioGeneratorMock, request, socket);
    app.run();

    assertEquals("\n" + bodyStr, request.getBody());

    app.stop();
  }

  @Test
  void echoServerReturnsInput() {
    var clientInput = "GET / HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "Content-Length: 68\n" +
            "\n\n";

    var bodyStr = "Body line 1: def\n" +
            "Body line 2: def\n" +
            "Body line 3: def\n" +
            "Body line 4: def";

    var ioGeneratorMock = new IOGeneratorMock(clientInput.concat(bodyStr));
    var request = new Request();
    var socket = new ServerSocketMock();
    var app = new EchoServer(ioGeneratorMock, request, socket);
    app.run();

    var printWriter = ioGeneratorMock.getPrintWriter();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 68\r\n\r\n" +
            request.getBody() +
            "\n";

    assertEquals(expected, printWriter.getSentData());

    app.stop();
  }
}