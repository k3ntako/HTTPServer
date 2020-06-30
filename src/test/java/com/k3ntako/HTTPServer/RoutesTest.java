package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.SimpleGet;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoutesTest {

  @Test
  void getSimpleGet() {
    var routes = new Routes();
    var simpleGet = routes.get("/simple_get");

    assertTrue(simpleGet instanceof SimpleGet);
  }

  @Test
  void getSimpleGetWithBody() {
    var routes = new Routes();
    var simpleGet = routes.get("/simple_get_with_body");

    assertTrue(simpleGet instanceof SimpleGetWithBody);
  }
}