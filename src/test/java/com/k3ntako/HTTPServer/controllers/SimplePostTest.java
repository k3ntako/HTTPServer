package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SimplePostTest {

  @Test
  void getResponse() throws IOException, HTTPError {
    var postBody = "hello post!";
    var request = new RequestMock("POST", "/simple_post", "HTTP/1.1", new HashMap<>(), postBody);
    var fileIOMock = new FileIOMock(postBody);

    var simplePost = new SimplePost(fileIOMock, new UUIDMock("8d142d80-565f-417d-8334-a8a19caadadb"));
    simplePost.getResponse(request);

    assertEquals(postBody, fileIOMock.getLastWrite());
    assertEquals("./data/8d142d80-565f-417d-8334-a8a19caadadb.txt", fileIOMock.getLastWritePath().toString());
  }

  @Test
  void getResponseReturnsFileName() throws IOException, HTTPError {
    var postBody = "hello post!";
    var request = new RequestMock("POST", "/simple_post", "HTTP/1.1", new HashMap<>(), postBody);
    var fileIOMock = new FileIOMock(postBody);

    var simplePost = new SimplePost(fileIOMock, new UUIDMock("8d142d80-565f-417d-8334-a8a19caadadb"));
    var response = simplePost.getResponse(request);
    var responseStr = response.createResponse();

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 36\r\n\r\n" +
        "8d142d80-565f-417d-8334-a8a19caadadb";

    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void getResponseThrowsErrorIfBodyIsMultipleLines() {
    var postBody = "hello post!\nsecond line";
    var request = new RequestMock("POST", "/simple_post", "HTTP/1.1", new HashMap<>(), postBody);
    var fileIOMock = new FileIOMock(postBody);

    var simplePost = new SimplePost(fileIOMock, new UUIDMock("8d142d80-565f-417d-8334-a8a19caadadb"));

    HTTPError exception = assertThrows(
        HTTPError.class,
        () -> simplePost.getResponse(request)
    );

    assertEquals("Request body should not be multiline", exception.getMessage());
    assertEquals(400, exception.getStatus());
  }
}