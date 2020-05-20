package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.EchoServerMock;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

  @Test
  void start() throws IOException {
    var input = new BufferedReader(new StringReader("echo\n"));
    var output = new PrintWriter(new StringWriter(), true);

    var echoServer = new EchoServerMock(input, output);
    var app = new App(echoServer);

    app.start();

    assertTrue(echoServer.createAndListenCalled);
    assertTrue(echoServer.closeCalled);

    assertEquals("echo", echoServer.sentData);
  }
}