package com.k3ntako.HTTPServer.routes;

import com.k3ntako.HTTPServer.Request;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundTest {

  @Test
  void getResponse() {
    var notFound = new NotFound();
    var response = notFound.getResponse(new Request());

    var expectedResponse = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, response.createResponse());
  }
}