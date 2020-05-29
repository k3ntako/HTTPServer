package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.IOMock;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EchoServerTest {
  @Test
  void echoServerReturnsInput() {
    var input = new BufferedReader(new StringReader("echo\n"));
    var output = new PrintWriter(new StringWriter(), true);

    var ioMock = new IOMock(input, output);
    var echoServer = new EchoServer(ioMock);

    echoServer.run();

    assertTrue(ioMock.acceptCalled);
    assertTrue(ioMock.startConnectionCalled);
    assertTrue(ioMock.closeCalled);

    assertEquals("echo", ioMock.sentData.get(0));
  }

  @Test
  void echoServerReturnsInputWithMultipleLines() {
    var input = new BufferedReader(new StringReader("echo\nhello\n"));
    var output = new PrintWriter(new StringWriter(), true);

    var ioMock = new IOMock(input, output);
    var echoServer = new EchoServer(ioMock);

    echoServer.run();


    assertTrue(ioMock.acceptCalled);
    assertTrue(ioMock.startConnectionCalled);
    assertTrue(ioMock.closeCalled);

    var expected = new ArrayList<String>();
    expected.add("echo");
    expected.add("hello");

    assertEquals(expected, ioMock.sentData);
  }
}