package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.Request;
import com.k3ntako.HTTPServer.mocks.ClientSocketIOMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundTest {

  @Test
  void getResponse() {
    var notFound = new NotFound();
    var clientSocketIO = new ClientSocketIOMock("GET / HTTP/1.1");
    var response = (ResponseMock) notFound.handleNotFound(new Request(clientSocketIO, new MimeTypes()), new ResponseMock());

    assertNull(response.getSetBodyArg);
    assertNull(response.getSetJsonBodyArg);
    assertEquals(404, response.getSetStatusArg);
  }
}