package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.k3ntako.HTTPServer.mocks.JsonIOMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

  @Test
  void createEmptyResponse() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    var responseBytes = response.createResponse();

    var expected = "HTTP/1.1 204 No Content\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertArrayEquals(expected.getBytes(), responseBytes);
  }

  @Test
  void createResponseWithBody() throws HTTPError {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setBody("This\nis\nthe\nresponse\nbody!!");

    var responseBytes = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 27\r\n\r\n" +
        "This\nis\nthe\nresponse\nbody!!";
    assertArrayEquals(expected.getBytes(), responseBytes);
  }

  @Test
  void createResponseWithJsonBody() throws HTTPError {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    var reminderCreator = ReminderJsonCreator.createReminder("123", "Do chores");
    response.setBody(reminderCreator);

    var responseBytes = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 31\r\n\r\n" +
        "{\"id\":\"123\",\"task\":\"Do chores\"}";
    assertArrayEquals(expected.getBytes(), responseBytes);
  }

  @Test
  void createResponseWithBinaryBody() throws HTTPError {
    var jsonIOMock = new JsonIOMock();
    var response = new Response(jsonIOMock);

    var bodyBytes = "binary body".getBytes();
    response.setBody(bodyBytes);

    var responseBytes = response.createResponse();

    var bodyLength = "binary body".length();
    var header = "HTTP/1.1 200 OK\r\nContent-Length: " +
        bodyLength +
        "\r\n\r\n";

    var headerBytes = header.getBytes();

    byte[] expected = new byte[headerBytes.length + bodyBytes.length];
    System.arraycopy(headerBytes, 0, expected, 0, headerBytes.length);
    System.arraycopy(bodyBytes, 0, expected, headerBytes.length, bodyBytes.length);

    assertArrayEquals(expected, responseBytes);
  }

  @Test
  void setStatus() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setStatus(404);

    var headerStr = response.createResponse();
    var expected = "HTTP/1.1 404 Not Found\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertArrayEquals(expected.getBytes(), headerStr);
  }

  @Test
  void addHeader() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setStatus(301);
    response.addHeader("Location", "/simple_get");

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 301 Moved Permanently\r\n" +
        "Location: /simple_get\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertArrayEquals(expected.getBytes(), headerStr);
  }

  @Test
  void setRedirect() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setRedirect("/test", 302);

    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 302 Found\r\n" +
        "Location: /test\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertArrayEquals(expected.getBytes(), headerStr);
  }

  @Test
  void throwErrorIfBodyIsNull() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    HTTPError exception = assertThrows(
        HTTPError.class, () -> response.setBody((String) null)
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }

  @Test
  void throwErrorIfJsonBodyIsNull() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    HTTPError exception = assertThrows(
        HTTPError.class, () -> response.setBody((JsonElement) null)
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }

  @Test
  void throwErrorIfBinaryBodyIsNull() {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    HTTPError exception = assertThrows(
        HTTPError.class, () -> response.setBody((byte[]) null)
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }
}
