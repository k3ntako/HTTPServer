package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatchSimplePostTest {

  @Test
  void getResponse() throws IOException {
    var patchBody = "hello post!";
    var request = new RequestMock("PATCH", "/simple_post", "HTTP/1.1", new HashMap<>(), patchBody);
    var fileIOMock = new FileIOMock(patchBody);

    var patchSimplePost = new PatchSimplePost(fileIOMock);
    var response = patchSimplePost.getResponse(request);

    assertEquals(patchBody, fileIOMock.getLastAppend());
    assertEquals("./data/simple_post.txt", fileIOMock.getLastAppendPath().toString());

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 0\r\n\r\n";
    assertEquals(expectedResponse, response.createResponse());
  }
}