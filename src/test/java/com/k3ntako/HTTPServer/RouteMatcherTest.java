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
    var hash = routeMatcher.matchRoute(routes, request);

    ControllerMethodInterface controllerMethod = (ControllerMethodInterface) hash.get("controllerMethod");
    assertNotNull(controllerMethod);

    HashMap<String, String> params = (HashMap<String, String>) hash.get("params");
    assertNotNull(params);
  }
}