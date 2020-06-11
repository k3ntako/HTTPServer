package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
  @Test
  void routeRequest() {
    var routes = new HashMap<String, RouteInterface>();
    routes.put("/simple_get_with_body", new SimpleGetWithBody());

    var request = new RequestMock("GET", "/simple_get_with_body", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routes);
    var response = router.routeRequest(request);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n\r\n" +
            "Hello world";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void notFoundRoute() {
    var routes = new HashMap<String, RouteInterface>();

    var request = new RequestMock("GET", "/not_valid", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routes);
    var response = router.routeRequest(request);

    var expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }
}