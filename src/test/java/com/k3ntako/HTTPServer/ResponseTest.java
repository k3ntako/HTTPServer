package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

  @Test
  void createResponse() {
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Length", "17");
    headers.put("Content-Type", "text/html; charset=UTF-8");


    var request = new RequestMock(
            "GET",
            "/",
            "HTTP/1.1",
            headers,
            "This\nis\nthe\nbody!"
    );

    var response = new Response(request);
    var headerStr = response.createResponse();

    var expected = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 17\r\n\r\n" +
            "This\nis\nthe\nbody!";
    assertEquals(expected, headerStr);
  }
}
