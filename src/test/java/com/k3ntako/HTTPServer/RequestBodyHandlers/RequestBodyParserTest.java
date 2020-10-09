package com.k3ntako.HTTPServer.RequestBodyHandlers;

import com.k3ntako.HTTPServer.RequestBodyParser;
import com.k3ntako.HTTPServer.mocks.SocketMock;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class RequestBodyParserTest {

  @Test
  void readTextBody() throws IOException {
    var bodyStr = "Body line 1: abc\n" +
        "Body line 2: abc\n" +
        "Body line 3: abc\n" +
        "Body line 4: abc\n";
    var socket = new SocketMock(bodyStr);

    var requestBodyParser = new RequestBodyParser();
    var body = requestBodyParser.parseBody(socket, bodyStr.length());

    assertEquals(bodyStr, new String(body));
  }

  @Test
  void readBinaryBody() throws IOException {
    var str = "This is text!";
    var bodyBinary = str.getBytes();
    var socket = new SocketMock(bodyBinary);

    var requestBodyParser = new RequestBodyParser();
    var body = (byte[]) requestBodyParser.parseBody(socket, bodyBinary.length);

    assertArrayEquals(bodyBinary, body);
  }

  @Test
  void readBinaryBodyShouldOnlyReadUntilContentLength() throws IOException {
    var str = "This is text!";
    var bodyBinary = str.getBytes();
    var socket = new SocketMock(bodyBinary);

    var requestBodyParser = new RequestBodyParser();
    var body = (byte[]) requestBodyParser.parseBody(socket, bodyBinary.length - 1);

    assertEquals(bodyBinary.length - 1, body.length);
  }

  @Test
  void readBinaryBodyReturnsEmptyArrIfLengthZero() throws IOException {
    var socket = new SocketMock("");

    var requestBodyParser = new RequestBodyParser();
    var body = (byte[]) requestBodyParser.parseBody(socket, 0);

    assertArrayEquals(new byte[]{}, body);
  }
}