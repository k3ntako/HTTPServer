package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.ServerIOMock;
import com.k3ntako.HTTPServer.mocks.RequestHandlerMock;
import com.k3ntako.HTTPServer.mocks.ServerSocketMock;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
  @Test
  void serverParsesHeader() {
    var clientInput = "GET /simple_get_with_body HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "\n\n";

    var serverIOMock = new ServerIOMock(clientInput);
    var requestHandlerMock = new RequestHandlerMock();
    var socket = new ServerSocketMock();

    var routes = new Routes();
    var router = new Router(routes);

    var app = new Server(serverIOMock, requestHandlerMock, socket, router);
    app.run();

    assertTrue( requestHandlerMock.wasHandleRequestCalled());
  }

  @Test
  void serverReturnsInput() {
    var clientInput = "GET /simple_get_with_body HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "Content-Length: 68\n" +
            "\n\n";

    var bodyStr = "Body line 1: def\n" +
            "Body line 2: def\n" +
            "Body line 3: def\n" +
            "Body line 4: def";

    var serverIO = new ServerIOMock(clientInput + bodyStr);
    var requestHandlerMock = new RequestHandlerMock();
    var socket = new ServerSocketMock();

    var routes = new Routes();
    var router = new Router(routes);

    var app = new Server(serverIO, requestHandlerMock, socket, router);
    app.run();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n\r\n" +
            "Hello world\n";

    assertEquals(expected, serverIO.getSentData());
  }
}