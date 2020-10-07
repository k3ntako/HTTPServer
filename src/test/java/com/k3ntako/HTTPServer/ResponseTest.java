package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.k3ntako.HTTPServer.mocks.FileTypeResolverMock;
import com.k3ntako.HTTPServer.mocks.JsonConverterMock;
import com.k3ntako.HTTPServer.utilities.JsonConverter;
import com.k3ntako.HTTPServer.utilities.ReminderJsonCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

  @Test
  void createEmptyResponse() {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());

    var responseBytes = response.createResponse();

    var expected = "HTTP/1.1 204 No Content\r\n\r\n";

    assertEquals(expected, new String(responseBytes));
  }

  @Test
  void createResponseWithBody() throws HTTPError {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());
    response.setBody("This\nis\nthe\nresponse\nbody!!");

    var responseBytes = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 27\r\n\r\n" +
        "This\nis\nthe\nresponse\nbody!!";

    assertEquals(expected, new String(responseBytes));
  }

  @Test
  void createResponseWithJsonBody() throws HTTPError {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());

    var reminderCreator = ReminderJsonCreator.createReminder("123", "Do chores");
    response.setBody(reminderCreator);

    var responseBytes = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: application/json\r\n" +
        "Content-Length: 31\r\n\r\n" +
        "{\"id\":\"123\",\"task\":\"Do chores\"}";

    assertEquals(expected, new String(responseBytes));
  }

  @Test
  void createResponseWithBinaryBody() throws HTTPError {
    var jsonConverterMock = new JsonConverterMock();
    var response = new Response(jsonConverterMock, new FileTypeResolverMock(null));

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
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());
    response.setStatus(404);

    var headerBytes = response.createResponse();
    var expected = "HTTP/1.1 404 Not Found\r\n\r\n";

    assertEquals(expected, new String(headerBytes));
  }

  @Test
  void addHeader() {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());
    response.setStatus(301);
    response.addHeader("Location", "/simple_get");

    var headerBytes = response.createResponse();

    var expected = "HTTP/1.1 301 Moved Permanently\r\n" +
        "Location: /simple_get\r\n\r\n";

    assertEquals(expected, new String(headerBytes));
  }

  @Test
  void setRedirect() {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());
    response.setRedirect("/test", 302);

    var headerBytes = response.createResponse();

    var expected = "HTTP/1.1 302 Found\r\n" +
        "Location: /test\r\n\r\n";

    assertEquals(expected, new String(headerBytes));
  }

  @Test
  void setBodySetsContentType() throws HTTPError {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock("image/jpeg"));

    var imageBytes = new byte[] {5, 27, 19, 95};
    response.setBody(imageBytes);

    var responseBytes = response.createResponse();

    var responseString = new String(responseBytes);

    assertTrue(responseString.contains("image/jpeg"));
  }

  @Test
  void setBodyDoesNotSetsContentTypeIfNull() throws HTTPError {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock(null));

    var imageBytes = new byte[] {5, 27, 19, 95};
    response.setBody(imageBytes);

    var responseBytes = response.createResponse();

    var responseString = new String(responseBytes);

    assertFalse(responseString.contains("Content-Type"));
  }

  @Test
  void setBodySetsContentTypeIfString() throws HTTPError {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock(null));

    var str = "body string";
    response.setBody(str);

    var responseBytes = response.createResponse();

    var responseString = new String(responseBytes);

    assertTrue(responseString.contains("text/plain"));
  }

  void setBodyWithCustomContentType() throws HTTPError {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());

    var str = "body string";
    response.setBody(str.getBytes(), "mock/mock-type");

    var responseBytes = response.createResponse();

    var responseString = new String(responseBytes);

    assertTrue(responseString.contains("mock/mock-type"));
  }

  @Test
  void throwErrorIfBodyIsNull() {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());

    HTTPError exception = assertThrows(
        HTTPError.class, () -> response.setBody((String) null)
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }

  @Test
  void throwErrorIfJsonBodyIsNull() {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());

    HTTPError exception = assertThrows(
        HTTPError.class, () -> response.setBody((JsonElement) null)
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }

  @Test
  void throwErrorIfBinaryBodyIsNull() {
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new FileTypeResolverMock());

    HTTPError exception = assertThrows(
        HTTPError.class, () -> response.setBody((byte[]) null)
    );

    assertEquals("Response body cannot be null", exception.getMessage());
    assertEquals(500, exception.getStatus());
  }
}
