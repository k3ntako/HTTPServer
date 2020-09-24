package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublicFilesTest {

  @Test
  void get() throws HTTPError {
    final var request = new RequestMock("GET", "/index.html");

    final var fileIO = new FileIOMock("<html></html>", false);
    final var publicFiles = new PublicFiles(fileIO);
    final var response = publicFiles.get(request);

    assertEquals("public/index.html", fileIO.getLastIsResourceDirectoryFileName());
    assertEquals("public/index.html", fileIO.getLastGetResourceIfExistsFileName());

    final var responseStr = response.createResponse();
    final var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html; charset=UTF-8\r\n" +
        "Content-Length: 13\r\n\r\n" +
        "<html></html>";
    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void getDirectoryDefaultsToIndexHTML() throws HTTPError {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock("<html></html>", true);
    final var publicFiles = new PublicFiles(fileIO);
    final var response = publicFiles.get(request);

    assertEquals("public/", fileIO.getLastIsResourceDirectoryFileName());
    assertEquals("public/index.html", fileIO.getLastGetResourceIfExistsFileName());

    final var responseStr = response.createResponse();
    final var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html; charset=UTF-8\r\n" +
        "Content-Length: 13\r\n\r\n" +
        "<html></html>";
    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void getDirectoryFallsBackToDirectoryContent() throws HTTPError {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock(
        new String[]{null, "index.html\nindex.css"},
        new Boolean[]{true}
    );
    final var publicFiles = new PublicFiles(fileIO);
    final var response = publicFiles.get(request);

    assertEquals("public/", fileIO.getLastIsResourceDirectoryFileName());
    assertEquals("public/", fileIO.getLastGetResourceIfExistsFileName());

    final var responseStr = response.createResponse();
    final var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html; charset=UTF-8\r\n" +
        "Content-Length: 20\r\n\r\n" +
        "index.html\nindex.css";
    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void getDirectoryReturns404IfFileNotFound() {
    final var request = new RequestMock("GET", "/index.html");

    final var fileIO = new FileIOMock(null, false);
    final var publicFiles = new PublicFiles(fileIO);

    HTTPError exception = assertThrows(HTTPError.class, () -> publicFiles.get(request));

    assertEquals("File was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void getDirectoryReturns404IfDirectoryNotFound() {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock(null, (Boolean) null);
    final var publicFiles = new PublicFiles(fileIO);

    HTTPError exception = assertThrows(HTTPError.class, () -> publicFiles.get(request));

    assertEquals("File was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}