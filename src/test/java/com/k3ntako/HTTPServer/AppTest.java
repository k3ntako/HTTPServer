package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.EchoServerMock;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
  @Test
  void echoServerReturnsInput() {
    var input = new BufferedReader(new StringReader("echo\n"));
    var output = new PrintWriter(new StringWriter(), true);

    var echoServer = new EchoServerMock(input, output);
    var app = new App(echoServer, 3001);

    app.start();

    assertTrue(echoServer.createAndListenCalled);
    assertTrue(echoServer.closeCalled);

    assertEquals("echo", echoServer.sentData.get(0));
  }

  @Test
  void echoServerReturnsInputWithMultipleLines() {
    var input = new BufferedReader(new StringReader("echo\nhello\n"));
    var output = new PrintWriter(new StringWriter(), true);

    var echoServer = new EchoServerMock(input, output);
    var app = new App(echoServer, 3002);

    app.start();

    assertTrue(echoServer.createAndListenCalled);
    assertTrue(echoServer.closeCalled);

    var expected = new ArrayList<String>();
    expected.add("echo");
    expected.add("hello");

    assertEquals(expected, echoServer.sentData);
  }
}