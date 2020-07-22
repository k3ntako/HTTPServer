package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {
  @Test
  void handleRequest() throws Exception {
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();

    var router = new Router(routeRegistry);

    var requestHandler = new RequestHandler(router, new RequestGeneratorMock());

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 11\r\n\r\n" +
        "Hello world";

    var responseStr = requestHandler.handleRequest(new ServerIOMock(""));
    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void useErrorHandler() throws Exception {
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();

    var router = new Router(routeRegistry);

    var requestHandler = new RequestHandler(router, new RequestGeneratorMockThrowsError());
    requestHandler.useErrorHandler(new ErrorHandler());

    var responseStr = requestHandler.handleRequest(new ServerIOMock(""));

    var expectedResponse = "HTTP/1.1 500 Internal Server Error\r\n" +
        "Content-Length: 20\r\n\r\n" +
        "This is a test error";

    assertEquals(expectedResponse, responseStr);
  }
}