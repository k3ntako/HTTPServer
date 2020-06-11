package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.IOGeneratorMock;
import com.k3ntako.HTTPServer.mocks.RequestHandlerMock;
import com.k3ntako.HTTPServer.mocks.ServerSocketMock;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
  @Test
  void echoServerParsesHeader() {
    var clientInput = "GET /simple_get_with_body HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "User-Agent: curl/7.64.1\n" +
            "Accept: */*\n" +
            "\n\n";

    var ioGeneratorMock = new IOGeneratorMock(clientInput);
    var requestHandlerMock = new RequestHandlerMock();
    var socket = new ServerSocketMock();

    var routes = new HashMap<String, RouteInterface>();
    routes.put("/simple_get_with_body", new SimpleGetWithBody());
    var router = new Router(routes);

    var app = new Server(ioGeneratorMock, requestHandlerMock, socket, router);
    app.run();

    assertTrue( requestHandlerMock.wasHandleRequestCalled());
    app.stop();
  }

  @Test
  void echoServerReturnsInput() {
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

    var ioGeneratorMock = new IOGeneratorMock(clientInput + bodyStr);
    var requestHandlerMock = new RequestHandlerMock();
    var socket = new ServerSocketMock();

    var routes = new HashMap<String, RouteInterface>();
    routes.put("/simple_get_with_body", new SimpleGetWithBody());
    var router = new Router(routes);

    var app = new Server(ioGeneratorMock, requestHandlerMock, socket, router);
    app.run();

    var printWriter = ioGeneratorMock.getPrintWriter();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n\r\n" +
            "Hello world\n";

    assertEquals(expected, printWriter.getSentData());

    app.stop();
  }
}