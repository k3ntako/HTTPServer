package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.GetTextFileContent;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistryTest {

  @Test
  void registerAGetRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));

    var request = new RequestMock("GET", "/simple_get");
    var simpleGet = routeRegistry.getController(request);
    assertNotNull(simpleGet);
  }

  @Test
  void registerAPostRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    var textFile = new TextFile(new FileIOMock(), new UUID());
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req) -> new Reminders(textFile).post(req));

    var request = new RequestMock("POST", "/reminders");
    var remindersPost = routeRegistry.getController(request);
    assertNotNull(remindersPost);
  }

  @Test
  void registerALowercaseMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    var textFile = new TextFile(new FileIOMock(), new UUID());
    routeRegistry.registerRoute("post", "/reminders", (RequestInterface req) -> new Reminders(textFile).post(req));

    var request = new RequestMock("POST", "/reminders");
    var remindersPost = routeRegistry.getController(request);
    assertNotNull(remindersPost);
  }

  @Test
  void registerMultipleRoutesWithSameMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    routeRegistry.registerRoute("GET", "/simple_get_with_body", (RequestInterface req) -> new SimpleGetWithBody().get(req));


    var request = new RequestMock("GET", "/simple_get");
    var simpleGet = routeRegistry.getController(request);
    assertNotNull(simpleGet);

    var requestWithBody = new RequestMock("GET", "/simple_get_with_body");
    var simpleGetWithBody = routeRegistry.getController(requestWithBody);
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
    var request = new RequestMock("GET", "/simple_get");
    var simpleGet = routeRegistry.getController(request);

    assertNull(simpleGet);
  }

  @Test
  void getControllerForVariableRoute() throws Exception {
    var mockFileContent = "Hello!";
    var fileIO = new FileIOMock(mockFileContent);

    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/reminders/:id", (RequestInterface req) -> new GetTextFileContent(fileIO).get(req));

    var request = new RequestMock("GET", "/reminders/8d142d80-565f-417d-8334-a8a19caadadb");
    var remindersGet = routeRegistry.getController(request);

    assertNotNull(remindersGet);


    ControllerMethodInterface controllerMethod = remindersGet.getControllerMethod();
    var response = controllerMethod.getResponse(request);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 6\r\n\r\n" +
        "Hello!";
    assertEquals(expectedResponse, response.createResponse());
  }
}