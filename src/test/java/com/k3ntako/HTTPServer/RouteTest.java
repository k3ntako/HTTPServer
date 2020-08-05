package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
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
  void getAndSetParams() {
    var route = new Route("/reminders/123");
    var textFile = new TextFile(new   FileIOMock(), new UUID());
    route.setControllerMethod((RequestInterface req) -> new Reminders(textFile).get(req));

    var params = new HashMap<String, String>();
    params.put("id", "123");
    route.setParams(params);

    var paramFromRoute = route.getParams();
    assertEquals("123", paramFromRoute.get("id"));
  }
}