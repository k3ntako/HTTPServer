package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.*;
import com.k3ntako.HTTPServer.utilities.FileExtensions;
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

    var clientSocketIO = new ClientSocketIOMock(clientInput);
    var requestGeneratorMock = new RequestGeneratorMock();
    var socket = new ServerSocketMock();

    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions()
    );
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, requestGeneratorMock, new ErrorHandler());

    var app = new Server(clientSocketIO, requestHandler, socket);
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

    var clientSocketIO = new ClientSocketIOMock(clientInput, bodyStr);
    var requestGeneratorMock = new RequestGeneratorMock();
    var socket = new ServerSocketMock();

    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions()
    );
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, requestGeneratorMock, new ErrorHandler());

    var app = new Server(clientSocketIO, requestHandler, socket);
    app.run();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 11\r\n\r\n" +
        "Hello world";

    assertEquals(expected, new String(clientSocketIO.getSentData()));
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

    var clientSocketIO = new ClientSocketIOMock(clientInput, bodyStr);
    var requestGeneratorMock = new RequestGeneratorMockThrowsError();
    var socket = new ServerSocketMock();

    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions()
    );
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);
    var requestHandler = new RequestHandler(router, requestGeneratorMock, new ErrorHandler());

    var app = new Server(clientSocketIO, requestHandler, socket);
    app.run();

    var expected = "HTTP/1.1 500 Internal Server Error\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 20\r\n\r\n" +
        "This is a test error";

    assertEquals(expected, new String(clientSocketIO.getSentData()));
  }
}