package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistrarTest {

  @Test
  void getSimpleGet() throws Exception {
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();
    var simpleGet = routeRegistry.getController("GET","/simple_get");

    assertTrue(simpleGet instanceof SimpleGet);
  }

  @Test
  void getSimpleGetWithBody() throws Exception {
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), new UUID());
    var routeRegistry = routeRegistrar.registerRoutes();
    var simpleGet = routeRegistry.getController("GET","/simple_get_with_body");

    assertTrue(simpleGet instanceof SimpleGetWithBody);
  }
}