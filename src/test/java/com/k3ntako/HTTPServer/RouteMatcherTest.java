package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouteMatcherTest {

  @Test
  void matchRoute() {
    var request = new RequestMock("POST", "/reminders");
    var routes = new HashMap<String, HashMap<String, ControllerMethodInterface>>();
    var routesForMethod = new HashMap<String, ControllerMethodInterface>();

    routesForMethod.put("/reminders", (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).post(req, res));

    routes.put("POST", routesForMethod);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(routes, request);

    assertNotNull(route.getControllerMethod());
    assertNotNull(route.getRouteParams());
  }

  @Test
  void matchVariableRoute() {
    var request = new RequestMock("GET", "/reminders/8d142d80-565f-417d-8334-a8a19caadadb");
    var routes = new HashMap<String, HashMap<String, ControllerMethodInterface>>();
    var routesForMethod = new HashMap<String, ControllerMethodInterface>();

    routesForMethod.put("/reminders/:id", (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).get(req, res));

    routes.put("GET", routesForMethod);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(routes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/reminders/8d142d80-565f-417d-8334-a8a19caadadb", route.getRoutePath());

    var routeParams = route.getRouteParams();
    assertNotNull(routeParams);
    assertEquals("8d142d80-565f-417d-8334-a8a19caadadb", routeParams.get("id"));
  }

  @Test
  void noVerbMatchShouldReturnNull() {
    var request = new RequestMock("GET", "/reminders");
    var routes = new HashMap<String, HashMap<String, ControllerMethodInterface>>();

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(routes, request);

    assertNull(route);
  }

  @Test
  void noControllerMethodMatchShouldReturnNull() {
    var request = new RequestMock("GET", "/reminders");
    var routes = new HashMap<String, HashMap<String, ControllerMethodInterface>>();
    var routesForMethod = new HashMap<String, ControllerMethodInterface>();
    routes.put("GET", routesForMethod);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(routes, request);

    assertNull(route);
  }
}