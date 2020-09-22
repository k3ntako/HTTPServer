package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.Request;
import com.k3ntako.HTTPServer.mocks.ClientSocketIOMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundTest {

  @Test
  void getResponse() {
    var notFound = new NotFound();
    var clientSocketIO = new ClientSocketIOMock("GET / HTTP/1.1");
    var response = (ResponseMock) notFound.handleNotFound(new Request(clientSocketIO), new ResponseMock());

    assertNull(response.setBodyArg);
    assertNull(response.setJsonBodyArg);
    assertEquals(404, response.setStatusArg);
  }
}