package com.k3ntako;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HTTPServerTest {

  @Test
  void addOne() {
    var httpServer = new HTTPServer();
    var result = httpServer.addOne(1);
    assertEquals(2, result);
  }
}