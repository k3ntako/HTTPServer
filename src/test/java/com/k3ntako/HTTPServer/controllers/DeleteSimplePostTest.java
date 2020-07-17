package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DeleteSimplePostTest {

  @Test
  void getResponse() throws IOException {
    var request = new RequestMock("DELETE", "/simple_post", "HTTP/1.1", new HashMap<>(), null);
    var fileIOMock = new FileIOMock();

    var deleteSimplePost = new DeleteSimplePost(fileIOMock);
    var response = deleteSimplePost.getResponse(request);

    assertEquals("./data/simple_post.txt", fileIOMock.getLastDeletePath().toString());

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, response.createResponse());
  }
}