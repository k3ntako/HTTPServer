package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.FileIOInterface;
import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.ReminderIOInterface;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexTest {
  @Test
  void get() throws HTTPError {
    var request = new RequestMock("GET", "/");

    var fileIO = new FileIOMock("<html></html>");
    var index = new Index(fileIO);
    var response = index.get(request);

    assertEquals("./pages/index.html", fileIO.getLastGetResourceFileName());

    var responseStr = response.createResponse();
    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html; charset=UTF-8\r\n" +
        "Content-Length: 13\r\n\r\n" +
        "<html></html>";
    assertEquals(expectedResponse, responseStr);
  }
}