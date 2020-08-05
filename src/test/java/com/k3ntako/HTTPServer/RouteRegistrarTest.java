package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistrarTest {

  @Test
  void getSimpleGet() throws Exception {
    var textFile = new TextFile(new FileIOMock(), new UUID());
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), textFile);
    var routeRegistry = routeRegistrar.registerRoutes();
    var request = new RequestMock("GET", "/simple_get");
    var simpleGet = routeRegistry.getController(request);

    assertNotNull(simpleGet);
  }

  @Test
  void getSimpleGetWithBody() throws Exception {
    var textFile = new TextFile(new FileIOMock(), new UUID());
    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIOMock(), textFile);
    var routeRegistry = routeRegistrar.registerRoutes();
    var request = new RequestMock("GET", "/simple_get_with_body");
    var simpleGetWithBody = routeRegistry.getController(request);

    assertNotNull(simpleGetWithBody);
  }
}