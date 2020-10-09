package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.RequestBodyParserMock;
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

    var requestBodyParser = new RequestBodyParserMock(bodyStr);
    var clientSocketIO = new ClientSocketIO(requestBodyParser);
    clientSocketIO.init(socket);
    var body = clientSocketIO.parseBody(bodyStr.length());

    assertEquals(bodyStr, new String(body));
    assertEquals(bodyStr.length(), requestBodyParser.contentLength);
  }

  @Test
  void readBinaryBody() throws IOException {
    var str = "This is text!";
    var bodyBinary = str.getBytes();
    var socket = new SocketMock(bodyBinary);

    var requestBodyParser = new RequestBodyParserMock(bodyBinary);
    var clientSocketIO = new ClientSocketIO(requestBodyParser);
    clientSocketIO.init(socket);
    var body = (byte[]) clientSocketIO.parseBody(bodyBinary.length);

    assertArrayEquals(bodyBinary, body);
    assertEquals(bodyBinary.length, requestBodyParser.contentLength);
  }
}