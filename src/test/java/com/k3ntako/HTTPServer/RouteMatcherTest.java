package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.Images;
import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.utilities.UUID;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RouteMatcherTest {

  @Test
  void matchRoute() {
    var request = new RequestMock("POST", "/reminders");
    var registeredRoutes = new ArrayList<RegisteredRoute>();
    var reminderRoute = new RegisteredRoute(
        "POST",
        "/reminders",
        (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).post(req, res)
    );

    registeredRoutes.add(reminderRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertNotNull(route.getRouteParams());
  }

  @Test
  void matchVariableRoute() {
    var request = new RequestMock("GET", "/reminders/8d142d80-565f-417d-8334-a8a19caadadb");
    var registeredRoutes = new ArrayList<RegisteredRoute>();
    var reminderRoute = new RegisteredRoute(
        "GET",
        "/reminders/:id",
        (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).get(req, res)
    );

    registeredRoutes.add(reminderRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/reminders/8d142d80-565f-417d-8334-a8a19caadadb", route.getRouteUrl());

    var routeParams = route.getRouteParams();
    assertNotNull(routeParams);
    assertEquals("8d142d80-565f-417d-8334-a8a19caadadb", routeParams.get("id"));
  }

  @Test
  void matchVariableRouteWithStar() {
    var request = new RequestMock("GET", "/pictures/dogs");

    var dataDirectory = new DataDirectoryIO(new FileIOMock(), "./mock");
    var registeredRoutes = new ArrayList<RegisteredRoute>();
    var imageRoute = new RegisteredRoute(
        "GET",
        "/pictures/*",
        (RequestInterface req, ResponseInterface res) -> new Images(dataDirectory, new UUID()).get(req, res)
    );

    registeredRoutes.add(imageRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/pictures/dogs", route.getRouteUrl());
  }

  @Test
  void matchVariableRouteWithStarWithMoreThanOneSlash() {
    var request = new RequestMock("GET", "/pictures/dogs/shepherd");
    var dataDirectory = new DataDirectoryIO(new FileIOMock(), "./mock");
    var registeredRoutes = new ArrayList<RegisteredRoute>();
    var imageRoute = new RegisteredRoute(
        "GET",
        "/pictures/*",
        (RequestInterface req, ResponseInterface res) -> new Images(dataDirectory, new UUID()).get(req, res)
    );

    registeredRoutes.add(imageRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/pictures/dogs/shepherd", route.getRouteUrl());
  }

  @Test
  void matchVariableRouteWithMultipleStars() {
    var request = new RequestMock("GET", "/pictures/dogs/shepherd");
    var dataDirectory = new DataDirectoryIO(new FileIOMock(), "./mock");
    var registeredRoutes = new ArrayList<RegisteredRoute>();
    var imageRoute = new RegisteredRoute(
        "GET",
        "/*/dogs/*",
        (RequestInterface req, ResponseInterface res) -> new Images(dataDirectory, new UUID()).get(req, res)
    );

    registeredRoutes.add(imageRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/pictures/dogs/shepherd", route.getRouteUrl());
  }


  @Test
  void matchRoutesInOrderRegistered() {
    var request = new RequestMock("GET", "/pictures/dogs/shepherd");

    var dataDirectory = new DataDirectoryIO(new FileIOMock(), "./mock");
    var registeredRoutes = new ArrayList<RegisteredRoute>();

    ControllerMethodInterface imageControllerMethod =
        (RequestInterface req, ResponseInterface res) -> new Images(dataDirectory, new UUID()).get(req, res);
    var imageRoute = new RegisteredRoute(
        "GET",
        "/*/dogs/*",
        imageControllerMethod
    );

    ControllerMethodInterface reminderControllerMethod =
        (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).get(req, res);

    var starRoute = new RegisteredRoute(
        "GET",
        "/*",
        reminderControllerMethod
    );

    registeredRoutes.add(imageRoute);
    registeredRoutes.add(starRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/pictures/dogs/shepherd", route.getRouteUrl());
    assertEquals(imageControllerMethod, route.getControllerMethod());
  }

  @Test
  void matchRoutesInOrderRegisteredTwo() {
    var request = new RequestMock("GET", "/pictures/dogs/shepherd");

    var dataDirectory = new DataDirectoryIO(new FileIOMock(), "./mock");
    var registeredRoutes = new ArrayList<RegisteredRoute>();

    ControllerMethodInterface imageControllerMethod =
        (RequestInterface req, ResponseInterface res) -> new Images(dataDirectory, new UUID()).get(req, res);
    var imageRoute = new RegisteredRoute(
        "GET",
        "/*/dogs/*",
        imageControllerMethod
    );

    ControllerMethodInterface reminderControllerMethod =
        (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).get(req, res);

    var starRoute = new RegisteredRoute(
        "GET",
        "/*",
        reminderControllerMethod
    );

    registeredRoutes.add(starRoute);
    registeredRoutes.add(imageRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNotNull(route.getControllerMethod());
    assertEquals("/pictures/dogs/shepherd", route.getRouteUrl());
    assertEquals(reminderControllerMethod, route.getControllerMethod());
  }

  @Test
  void doesNotMatchVariableRouteWithStar() {
    var request = new RequestMock("GET", "/pictures/dogs/shepherd");
    var dataDirectory = new DataDirectoryIO(new FileIOMock(), "./mock");
    var registeredRoutes = new ArrayList<RegisteredRoute>();
    var imageRoute = new RegisteredRoute(
        "GET",
        "/*/dogs/corgi",
        (RequestInterface req, ResponseInterface res) -> new Images(dataDirectory, new UUID()).get(req, res)
    );

    registeredRoutes.add(imageRoute);

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNull(route);
  }

  @Test
  void noMatchShouldReturnNull() {
    var request = new RequestMock("GET", "/reminders");
    var registeredRoutes = new ArrayList<RegisteredRoute>();

    var routeMatcher = new RouteMatcher();
    var route = routeMatcher.matchRoute(registeredRoutes, request);

    assertNull(route);
  }
}