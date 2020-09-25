package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistrarTest {

  @Test
  void getSimpleGet() throws Exception {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock()
    );
    var routeRegistry = routeRegistrar.registerRoutes();
    var request = new RequestMock("GET", "/simple_get");
    var simpleGet = routeRegistry.getController(request);

    assertNotNull(simpleGet);
  }

  @Test
  void getSimpleGetWithBody() throws Exception {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock()
    );
    var routeRegistry = routeRegistrar.registerRoutes();
    var request = new RequestMock("GET", "/simple_get_with_body");
    var simpleGetWithBody = routeRegistry.getController(request);

    assertNotNull(simpleGetWithBody);
  }
}