package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
  @Test
  void serverParsesHeader() throws Exception {
    var clientInput = "GET /simple_get_with_body HTTP/1.1\n" +
        "Host: localhost:5000\n" +
        "User-Agent: curl/7.64.1\n" +
        "Accept: */*\n" +
        "\n\n";

    var serverIOMock = new ServerIOMock(clientInput);
    var requestGeneratorMock = new RequestGeneratorMock();
    var socket = new ServerSocketMock();

    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, requestGeneratorMock);

    var app = new Server(serverIOMock, requestHandler, socket, router);
    app.run();

    assertTrue(requestGeneratorMock.wasHandleRequestCalled());
  }

  @Test
  void serverReturnsInput() throws Exception {
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
    var requestGeneratorMock = new RequestGeneratorMock();
    var socket = new ServerSocketMock();

    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, requestGeneratorMock);

    var app = new Server(serverIO, requestHandler, socket, router);
    app.run();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 11\r\n\r\n" +
        "Hello world\n";

    assertEquals(expected, serverIO.getSentData());
  }

  @Test
  void serverCatchesError() throws Exception {
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
    var requestGeneratorMock = new RequestGeneratorMockThrowsError();
    var socket = new ServerSocketMock();

    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, requestGeneratorMock);
    requestHandler.useErrorHandler(new ErrorHandler());

    var app = new Server(serverIO, requestHandler, socket, router);
    app.run();

    var expected = "HTTP/1.1 500 Internal Server Error\r\n" +
        "Content-Length: 20\r\n\r\n" +
        "This is a test error\n";

    assertEquals(expected, serverIO.getSentData());
  }
}