package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.SimpleGet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistryTest {

  @Test
  void registerGet() {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerGet("/simple_get", new SimpleGet());

    var simpleGet = routeRegistry.get("/simple_get");
    assertTrue(simpleGet instanceof SimpleGet);
  }

  @Test
  void registerPost() {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerPost("/simple_post", new SimpleGet());

    var simpleGet = routeRegistry.post("/simple_post");
    assertTrue(simpleGet instanceof SimpleGet);
  }
}