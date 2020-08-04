package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GetTextFileContentTest {

  @Test
  void getResponse() throws IOException {
    var content = "text file content!";
    var request = new RequestMock(
        "GET",
        "/text_file_content",
        "HTTP/1.1",
        new HashMap<>(),
        ""
    );

    var fileIOMock = new FileIOMock(content);

    var getTextFileContent = new GetTextFileContent(fileIOMock);
    var response = getTextFileContent.get(request);

    assertEquals("./data/reminders.txt", fileIOMock.getLastReadPath().toString());

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 18\r\n\r\n" +
        content;

    assertEquals(expectedResponse, response.createResponse());
  }
}