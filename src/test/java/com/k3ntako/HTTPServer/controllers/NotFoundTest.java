package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.Request;
import com.k3ntako.HTTPServer.mocks.ClientSocketIOMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundTest {

  @Test
  void getResponse() throws HTTPError {
    var notFound = new NotFound();
    var clientSocketIO = new ClientSocketIOMock("GET / HTTP/1.1");
    var response = notFound.handleNotFound(new Request(clientSocketIO));

    var expectedResponse = "HTTP/1.1 404 Not Found\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, response.createResponse());
  }
}