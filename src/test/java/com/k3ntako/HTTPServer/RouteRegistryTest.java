package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.controllers.SimpleGet;
import com.k3ntako.HTTPServer.controllers.SimpleGetWithBody;
import com.k3ntako.HTTPServer.controllers.Reminders;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegistryTest {

  @Test
  void registerAGetRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req, ResponseInterface res) -> new SimpleGet().get(req, res));

    var request = new RequestMock("GET", "/simple_get");
    var simpleGet = routeRegistry.getController(request);
    assertNotNull(simpleGet);
  }

  @Test
  void registerAPostRoute() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).post(req, res));

    var request = new RequestMock("POST", "/reminders");
    var remindersPost = routeRegistry.getController(request);
    assertNotNull(remindersPost);
  }

  @Test
  void registerALowercaseMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("post", "/reminders", (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).post(req, res));

    var request = new RequestMock("POST", "/reminders");
    var remindersPost = routeRegistry.getController(request);
    assertNotNull(remindersPost);
  }

  @Test
  void registerMultipleRoutesWithSameMethod() throws Exception {
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req, ResponseInterface res) -> new SimpleGet().get(req, res));
    routeRegistry.registerRoute("GET", "/simple_get_with_body", (RequestInterface req, ResponseInterface res) -> new SimpleGetWithBody().get(req, res));


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
      routeRegistry.registerRoute("GETS", "/simple_get", (RequestInterface req, ResponseInterface res) -> new SimpleGet().get(req, res));
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
    var mockUUID = new UUIDMock();
    var routeRegistry = new RouteRegistry();
    routeRegistry.registerRoute("GET", "/reminders/:id", (RequestInterface req, ResponseInterface res) -> new Reminders(new ReminderIOMock()).get(req, res));

    var request = new RequestMock("GET", "/reminders/" + mockUUID.getDefaultUUID());
    var routeParams = new HashMap<String, String>();
    routeParams.put("id", mockUUID.getDefaultUUID());
    request.setRouteParams(routeParams);
    var remindersGet = routeRegistry.getController(request);

    assertNotNull(remindersGet);

    ControllerMethodInterface controllerMethod = remindersGet.getControllerMethod();
    var response = (ResponseMock) controllerMethod.getResponse(request, new ResponseMock());

    var responseJson = (JsonObject) response.setJsonBodyArg;
    assertEquals("ReminderIOMock.getReminderByIds: mock task text", responseJson.get("task").getAsString());
  }
}