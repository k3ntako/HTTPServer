package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.GetTextFileContent;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

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
    var textFile = new TextFile(new FileIOMock(), new UUID());
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req) -> new Reminders(textFile).post(req));

    var remindersPost = routeRegistry.getController("POST", "/reminders");
    assertNotNull(remindersPost);
  }

  @Test
  void registerALowercaseMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    var textFile = new TextFile(new FileIOMock(), new UUID());
    routeRegistry.registerRoute("post", "/reminders", (RequestInterface req) -> new Reminders(textFile).post(req));

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
    var simpleGet = routeRegistry.getController("GET", "/simple_get");

    assertNull(simpleGet);
  }

  @Test
  void getControllerForVariableRoute() throws Exception {
    var mockFileContent = "Hello!";
    var fileIO = new FileIOMock(mockFileContent);

    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/reminders/:id", (RequestInterface req) -> new GetTextFileContent(fileIO).get(req));

    var remindersGet = routeRegistry.getController("GET", "/reminders/8d142d80-565f-417d-8334-a8a19caadadb");

    assertNotNull(remindersGet);

    var request = new RequestMock("GET", "/reminders/8d142d80-565f-417d-8334-a8a19caadadb", "HTTP/1.1", new HashMap<>(), "");

    ControllerMethodInterface controllerMethod = (ControllerMethodInterface) remindersGet.get("controllerMethod");
    var response = controllerMethod.getResponse(request);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 6\r\n\r\n" +
        "Hello!";
    assertEquals(expectedResponse, response.createResponse());
  }
}