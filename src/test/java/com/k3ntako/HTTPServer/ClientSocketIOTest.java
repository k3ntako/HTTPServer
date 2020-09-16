package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.SocketMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientSocketIOTest {

  @Test
  void readTextBody() throws IOException {
    var bodyStr = "Body line 1: abc\n" +
        "Body line 2: abc\n" +
        "Body line 3: abc\n" +
        "Body line 4: abc\n";
    var socket = new SocketMock(bodyStr);

    var clientSocketIO = new ClientSocketIO();
    clientSocketIO.init(socket);
    var body = clientSocketIO.readTextBody(bodyStr.length());

    assertEquals(bodyStr, body);
  }

  @Test
  void readBinaryBody() throws IOException {
    var str = "This is text!";
    var bodyBinary = str.getBytes();
    var socket = new SocketMock(bodyBinary);

    var clientSocketIO = new ClientSocketIO();
    clientSocketIO.init(socket);
    var body = clientSocketIO.readBinaryBody(bodyBinary.length);

    assertArrayEquals(bodyBinary, body);
  }

  @Test
  void readBinaryBodyShouldOnlyReadUntilContentLength() throws IOException {
    var str = "This is text!";
    var bodyBinary = str.getBytes();
    var socket = new SocketMock(bodyBinary);

    var clientSocketIO = new ClientSocketIO();
    clientSocketIO.init(socket);
    var body = clientSocketIO.readBinaryBody(bodyBinary.length - 1);

    assertEquals(bodyBinary.length - 1, body.length);
  }

  @Test
  void readBinaryBodyReturnsEmptyArrIfLengthZero() throws IOException {
    var socket = new SocketMock("");

    var clientSocketIO = new ClientSocketIO();
    clientSocketIO.init(socket);
    var body = clientSocketIO.readBinaryBody(0);

    assertArrayEquals(new byte[]{}, body);
  }
}