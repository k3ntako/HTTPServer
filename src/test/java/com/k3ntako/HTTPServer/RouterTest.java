package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
  @Test
  void handleGetRequest() throws Exception {
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();

    var request = new RequestMock("GET", "/simple_get_with_body", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routeRegistry);
    var response = router.routeRequest(request);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 11\r\n\r\n" +
        "Hello world";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void handlePostRequest() throws Exception {
    var uuidMock = new UUIDMock("17a5fb46-3a60-49a6-af43-d146d7943b39");
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), uuidMock);
    var routeRegistry = routeRegistrar.registerRoutes();

    var request = new RequestMock("POST", "/reminder_post", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routeRegistry);
    var response = router.routeRequest(request);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 36\r\n\r\n" +
        "17a5fb46-3a60-49a6-af43-d146d7943b39";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void notFoundRoute() throws Exception {
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();

    var request = new RequestMock("GET", "/not_valid", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routeRegistry);
    var response = router.routeRequest(request);

    var expectedResponse = "HTTP/1.1 404 Not Found\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }
}