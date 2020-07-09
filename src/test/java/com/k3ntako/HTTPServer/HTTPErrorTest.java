package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HTTPErrorTest {

  @Test
  void getErrorInformation() {
    var error = new HTTPError(500, "This is an error message");

    assertEquals(500, error.getStatus());
    assertEquals("This is an error message", error.getMessage());
  }
}