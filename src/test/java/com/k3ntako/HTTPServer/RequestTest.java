package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.ClientSocketIOMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

  @Test
  void parseRequest() throws IOException {
    var headerStr = "GET / HTTP/1.1\r\n" +
        "Host: localhost:5000\r\n" +
        "User-Agent: curl/7.64.1\r\n" +
        "Accept: */*\r\n\r\n";
    var clientSocketIO = new ClientSocketIOMock(headerStr);
    clientSocketIO.init(new Socket());

    var request = new Request(clientSocketIO);

    request.parseRequest();
    assertEquals("GET", request.getMethod());
    assertEquals("/", request.getRoute());
    assertEquals("HTTP/1.1", request.getProtocol());

    assertEquals("localhost:5000", request.getHeaders().get("Host"));
    assertEquals("curl/7.64.1", request.getHeaders().get("User-Agent"));
    assertEquals("*/*", request.getHeaders().get("Accept"));
  }

  @Test
  void parseBody() throws IOException {
    var header = "GET / HTTP/1.1\r\n" +
        "Content-Length: 68\r\n\r\n";
    var bodyStr = "Body line 1: abc\n" +
        "Body line 2: abc\n" +
        "Body line 3: abc\n" +
        "Body line 4: abc\n";

    var clientSocketIO = new ClientSocketIOMock(header, bodyStr);
    clientSocketIO.init(new Socket());
    var request = new Request(clientSocketIO);

    request.parseRequest();

    assertEquals(bodyStr, request.getBody());
  }

  @Test
  void getAndSetRouteParams() {
    var headerStr = "GET / HTTP/1.1\r\n" +
        "Host: localhost:5000\r\n" +
        "User-Agent: curl/7.64.1\r\n" +
        "Accept: */*\r\n\r\n";
    var clientSocketIO = new ClientSocketIOMock(headerStr);
    clientSocketIO.init(new Socket());

    var request = new Request(clientSocketIO);

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", "123");
    routeParams.put("event_id", "345");

    request.setRouteParams(routeParams);

    var requestParams = request.getRouteParams();

    assertEquals("123", requestParams.get("id"));
    assertEquals("345", requestParams.get("event_id"));
  }

  @Test
  void getRouteParam() {
    var headerStr = "GET / HTTP/1.1\r\n" +
        "Host: localhost:5000\r\n" +
        "User-Agent: curl/7.64.1\r\n" +
        "Accept: */*\r\n\r\n";
    var clientSocketIO = new ClientSocketIOMock(headerStr);
    clientSocketIO.init(new Socket());

    var request = new Request(clientSocketIO);

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", "123");
    routeParams.put("event_id", "345");

    request.setRouteParams(routeParams);

    assertEquals("123", request.getRouteParam("id"));
    assertEquals("345", request.getRouteParam("event_id"));
  }

  @Test
  void parseBinaryBody() throws IOException {
    var header = "GET / HTTP/1.1\r\n" +
        "Content-Type: image/png\r\n" +
        "Content-Length: 5\r\n\r\n";

    var bodyBytes = new byte[]{1, 13, 127, 12, 1};


    var clientSocketIO = new ClientSocketIOMock(header, bodyBytes);
    clientSocketIO.init(new Socket());
    var request = new Request(clientSocketIO);

    request.parseRequest();

    assertArrayEquals(bodyBytes, (byte[]) request.getBody());
    assertEquals(5, ((byte[]) request.getBody()).length);
    assertEquals("5", request.getHeaders().get("Content-Length"));
  }

}
