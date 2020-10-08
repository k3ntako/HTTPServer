package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.RouteRegistrarMock;
import com.k3ntako.HTTPServer.utilities.FileExtensions;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {
  @Test
  void handleGetRequest() throws Exception {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions(),
        new MimeTypes()
    );
    var routeRegistry = routeRegistrar.registerRoutes();

    var request = new RequestMock("GET", "/api/simple_get_with_body", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routeRegistry);
    var response = router.routeRequest(request);
    var responseBytes = response.createResponse();

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 11\r\n\r\n" +
        "Hello world";

    assertEquals(expectedResponse, new String(responseBytes));
  }

  @Test
  void handlePostRequest() throws Exception {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");

    var routeRegistrar = new RouteRegistrar(
        new RouteRegistry(),
        fileIO,
        dataDirectoryIO,
        new ReminderIOMock(),
        new FileExtensions(),
        new MimeTypes()
    );
    var routeRegistry = routeRegistrar.registerRoutes();

    var request = new RequestMock("POST", "/api/reminders", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routeRegistry);
    var response = router.routeRequest(request);
    var responseBytes = response.createResponse();

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: application/json\r\n" +
        "Content-Length: 36\r\n\r\n" +
        "{\"id\":\"mock-new-list-id\",\"items\":{}}";

    assertEquals(expectedResponse, new String(responseBytes));
  }

  @Test
  void notFoundRoute() throws Exception {
    var routeRegistrar = new RouteRegistrarMock();
    var routeRegistry = routeRegistrar.registerRoutes();

    var request = new RequestMock("GET", "/api/not_valid", "HTTP/1.1", new HashMap<>(), "");
    var router = new Router(routeRegistry);
    var response = router.routeRequest(request);
    var responseBytes = response.createResponse();

    var expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";

    assertEquals(expectedResponse, new String(responseBytes));
  }
}