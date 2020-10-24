package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.*;
import com.k3ntako.HTTPServer.utilities.FileExtensions;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {
  @Test
  void handleRequest() throws Exception {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions(),
        new MimeTypes()
    );
    var routeRegistry = routeRegistrar.registerRoutes();

    var router = new Router(routeRegistry);

    var clientInput = "GET /simple_get HTTP/1.1\r\n" +
        "Host: localhost:5000\r\n" +
        "User-Agent: curl/7.64.1\r\n" +
        "Accept: */*\r\n" +
        "\n\r\n\r";

    var clientSocketIO = new ClientSocketIOMock(clientInput);
    var requestHandler = new RequestHandler(router, new RequestGeneratorMock(), new ErrorHandler(), clientSocketIO);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 11\r\n\r\n" +
        "Hello world";

    requestHandler.run();

    var responseBytes = clientSocketIO.getSentData();
    assertEquals(expectedResponse, new String(responseBytes));
  }

  @Test
  void handlesError() throws Exception {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions(),
        new MimeTypes()
    );
    var routeRegistry = routeRegistrar.registerRoutes();

    var router = new Router(routeRegistry);

    var clientSocketIO =  new ClientSocketIOMock("");
    var requestHandler = new RequestHandler(router, new RequestGeneratorMockThrowsError(), new ErrorHandler(), clientSocketIO);

    var expectedResponse = "HTTP/1.1 500 Internal Server Error\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 20\r\n\r\n" +
        "This is a test error";

    requestHandler.run();
    assertEquals(expectedResponse, new String(clientSocketIO.getSentData()));
  }
}