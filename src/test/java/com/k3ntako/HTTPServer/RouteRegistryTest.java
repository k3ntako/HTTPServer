package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistryTest {

  @Test
  void registerAGetRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));

    var simpleGet = routeRegistry.getController("GET", "/simple_get");
    assertNotNull(simpleGet);
  }

  @Test
  void registerAPostRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req) -> new Reminders(new FileIOMock(), new UUID()).post(req));

    var remindersPost = routeRegistry.getController("POST", "/reminders");
    assertNotNull(remindersPost);
  }

  @Test
  void registerALowercaseMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("post", "/reminders", (RequestInterface req) -> new Reminders(new FileIOMock(), new UUID()).post(req));

    var remindersPost = routeRegistry.getController("POST", "/reminders");
    assertNotNull(remindersPost);
  }

  @Test
  void registerMultipleRoutesWithSameMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    routeRegistry.registerRoute("GET", "/simple_get_with_body", (RequestInterface req) -> new SimpleGetWithBody().get(req));

    var simpleGet = routeRegistry.getController("GET", "/simple_get");
    assertNotNull(simpleGet);

    var simpleGetWithBody = routeRegistry.getController("GET", "/simple_get_with_body");
    assertNotNull(simpleGetWithBody);
  }

  @Test
  void registerInvalidMethod() {
    try {
      var routeRegistry = new RouteRegistry();
      routeRegistry.registerRoute("GETS", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    } catch (Exception exception) {
      assertEquals("Unknown method: GETS", exception.getMessage());
    }
  }

  @Test
  void getControllerThatDoesNotExist() {
    var routeRegistry = new RouteRegistry();
    var simpleGet = routeRegistry.getController("GET","/simple_get");

    assertNull(simpleGet);
  }
}