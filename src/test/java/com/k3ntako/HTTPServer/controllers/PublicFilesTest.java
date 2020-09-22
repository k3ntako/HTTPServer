package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublicFilesTest {

  @Test
  void get() throws HTTPError {
    final var request = new RequestMock("GET", "/index.html");

    final var fileIO = new FileIOMock("<html></html>", false);
    final var publicFiles = new PublicFiles(fileIO);
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/index.html", fileIO.getLastIsResourceDirectoryFileName());
    assertEquals("public/index.html", fileIO.getLastGetResourceFileName());

    assertNull(response.getSetJsonBodyArg);
    assertEquals("<html></html>", response.getSetBodyArg);
    final var headers = response.headers;
    assertEquals("text/html; charset=UTF-8", headers.get("Content-Type"));
  }

  @Test
  void getDirectoryDefaultsToIndexHTML() throws HTTPError {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock("<html></html>", true);
    final var publicFiles = new PublicFiles(fileIO);
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/", fileIO.getLastIsResourceDirectoryFileName());
    assertEquals("public/index.html", fileIO.getLastGetResourceFileName());

    assertNull(response.getSetJsonBodyArg);
    assertEquals("<html></html>", response.getSetBodyArg);
    final var headers = response.headers;
    assertEquals("text/html; charset=UTF-8", headers.get("Content-Type"));
  }

  @Test
  void getDirectoryFallsBackToDirectoryContent() throws HTTPError {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock(
        new String[]{null, "index.html\nindex.css"},
        new Boolean[]{true}
    );
    final var publicFiles = new PublicFiles(fileIO);
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/", fileIO.getLastIsResourceDirectoryFileName());
    assertEquals("public/", fileIO.getLastGetResourceFileName());

    assertNull(response.getSetJsonBodyArg);

    assertEquals("index.html\nindex.css", response.getSetBodyArg);
    final var headers = response.headers;
    assertEquals("text/html; charset=UTF-8", headers.get("Content-Type"));
  }

  @Test
  void getDirectoryReturns404IfFileNotFound() {
    final var request = new RequestMock("GET", "/index.html");

    final var fileIO = new FileIOMock(null, false);
    final var publicFiles = new PublicFiles(fileIO);

    HTTPError exception = assertThrows(HTTPError.class, () -> publicFiles.get(request, new ResponseMock()));

    assertEquals("File was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void getDirectoryReturns404IfDirectoryNotFound() {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock(null, (Boolean) null);
    final var publicFiles = new PublicFiles(fileIO);

    HTTPError exception = assertThrows(HTTPError.class, () -> publicFiles.get(request, new ResponseMock()));

    assertEquals("File was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}