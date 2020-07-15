package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.controllers.SimplePost;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistryTest {

  @Test
  void registerAGetRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", new SimpleGet());

    var simpleGet = routeRegistry.getController("GET", "/simple_get");
    assertTrue(simpleGet instanceof SimpleGet);
  }

  @Test
  void registerAPostRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("POST", "/simple_post", new SimplePost(new FileIOMock()));

    var simplePost = routeRegistry.getController("POST", "/simple_post");
    assertTrue(simplePost instanceof SimplePost);
  }

  @Test
  void registerALowercaseMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("post", "/simple_post", new SimplePost(new FileIOMock()));

    var simplePost = routeRegistry.getController("POST", "/simple_post");
    assertTrue(simplePost instanceof SimplePost);
  }

  @Test
  void registerMultipleRoutesWithSameMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", new SimpleGet());
    routeRegistry.registerRoute("GET", "/simple_get_with_body", new SimpleGetWithBody());

    var simpleGet = routeRegistry.getController("GET", "/simple_get");
    assertTrue(simpleGet instanceof SimpleGet);

    var simpleGetWithBody = routeRegistry.getController("GET", "/simple_get_with_body");
    assertTrue(simpleGetWithBody instanceof SimpleGetWithBody);
  }

  @Test
  void registerInvalidMethod() {
    try {
      var routeRegistry = new RouteRegistry();
      routeRegistry.registerRoute("GETS", "/simple_get", new SimpleGet());
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