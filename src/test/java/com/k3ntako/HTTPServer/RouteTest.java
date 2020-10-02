package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

  @Test
  void getRoutePath() {
    var route = new Route("/reminders/123");
    assertEquals("/reminders/123", route.getRouteUrl());
  }

  @Test
  void getAndSetControllerMethod() {
    var route = new Route("/simple_get");
    route.setControllerMethod((RequestInterface req, ResponseInterface res) -> new SimpleGet().get(req, res));

    var controllerMethod = route.getControllerMethod();
    assertNotNull(controllerMethod);
  }

  @Test
  void getAndSetRouteParams() {
    var route = new Route("/reminders/123");
    route.setControllerMethod((RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).get(req, res));

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", "123");
    route.setRouteParams(routeParams);

    var paramFromRoute = route.getRouteParams();
    assertEquals("123", paramFromRoute.get("id"));
  }
}