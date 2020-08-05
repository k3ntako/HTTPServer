package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouteMatcherTest {

  @Test
  void matchRoute() {
    var request = new RequestMock("POST", "/reminders", "HTTP/1.1", new HashMap<>(), "");
    var routes = new HashMap<String, ControllerMethodInterface>();
    var textFile = new TextFile(new FileIOMock(), new UUID());
    routes.put("/reminders", (RequestInterface req) -> new Reminders(textFile).post(req));

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(routes, request);

    assertNotNull(route.getControllerMethod());
    assertNotNull(route.getParams());
  }

  @Test
  void matchVariableRoute() {
    var request = new RequestMock("GET", "/reminders/8d142d80-565f-417d-8334-a8a19caadadb", "HTTP/1.1", new HashMap<>(), "");
    var routes = new HashMap<String, ControllerMethodInterface>();
    var textFile = new TextFile(new FileIOMock(), new UUID());
    routes.put("/reminders/:id", (RequestInterface req) -> new Reminders(textFile).get(req));

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(routes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/reminders/8d142d80-565f-417d-8334-a8a19caadadb", route.getRoutePath());

    var params = route.getParams();
    assertNotNull(params);
    assertEquals("8d142d80-565f-417d-8334-a8a19caadadb", params.get("id"));
  }
}