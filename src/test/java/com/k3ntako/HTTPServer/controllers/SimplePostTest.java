package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimplePostTest {

  @Test
  void getResponse() throws IOException {
    var postBody = "hello post!";
    var request = new RequestMock("POST", "/simple_post", "HTTP/1.1", new HashMap<>(), postBody);
    var fileIOMock = new FileIOMock(postBody);

    var simplePost = new SimplePost(fileIOMock);
    simplePost.getResponse(request);

    assertEquals(postBody, fileIOMock.getLastWrite());
    assertEquals("./data/simple_post.txt", fileIOMock.getLastWritePath().toString());
  }
}