package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

  @Test
  void getRoutePath() {
    var route = new Route("/reminders/123");
    assertEquals("/reminders/123", route.getRoutePath());
  }

  @Test
  void getAndSetControllerMethod() {
    var route = new Route("/simple_get");
    route.setControllerMethod((RequestInterface req) -> new SimpleGet().get(req));

    var controllerMethod = route.getControllerMethod();
    assertNotNull(controllerMethod);
  }

  @Test
  void getAndSetRouteParams() {
    var route = new Route("/reminders/123");
    route.setControllerMethod((RequestInterface req) -> new Reminders(new ReminderIOMock()).get(req));

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", "123");
    route.setRouteParams(routeParams);

    var paramFromRoute = route.getRouteParams();
    assertEquals("123", paramFromRoute.get("id"));
  }
}