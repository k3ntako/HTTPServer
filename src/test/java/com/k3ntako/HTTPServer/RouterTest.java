package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.routes.SimpleGet;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
  @Test
  void routeRequest() {
    var routes = new HashMap<String, RouteInterface>();
    routes.put("/simple-get", new SimpleGet());

    var request = new RequestMock("GET", "/simple-get", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routes);
    var response = router.routeRequest(request);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n\r\n" +
            "Hello world";

    assertEquals(expectedResponse, response.createResponse());
  }
}