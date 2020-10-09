package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PublicFilesTest {

  @Test
  void get() throws HTTPError, IOException {
    final var request = new RequestMock("GET", "/index.html");

    final var fileIO = new FileIOMock(new String[]{null, "<html></html>"});
    final var publicFiles = new PublicFiles(fileIO, new MimeTypes());
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/index.html", fileIO.getLastGetResourceIfExistsFileName());

    assertNull(response.getSetJsonBodyArg);
    assertEquals("<html></html>", new String(response.getSetBinaryBodyArg));
    final var headers = response.headers;
  }

  @Test
  void getDirectoryDefaultsToIndexHTML() throws HTTPError, IOException {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock("<html></html>");
    final var publicFiles = new PublicFiles(fileIO, new MimeTypes());
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/index.html", fileIO.getLastGetResourceIfExistsFileName());

    assertNull(response.getSetJsonBodyArg);
    assertEquals("<html></html>", new String(response.getSetBinaryBodyArg));
  }

  @Test
  void getNestRoutes() throws HTTPError, IOException {
    final var request = new RequestMock("GET", "/images/dogs/mocha.png");

    var mockReturns = new byte[][]{null, new byte[]{1, 2, 3, 4, 5, 6}};
    final var fileIO = new FileIOMock(mockReturns);
    final var publicFiles = new PublicFiles(fileIO, new MimeTypes());
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/images/dogs/mocha.png", fileIO.getLastGetResourceIfExistsFileName());

    assertNull(response.getSetJsonBodyArg);
    assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6}, response.getSetBinaryBodyArg);
  }

  @Test
  void getDirectoryFallsBackToDirectoryContent() throws HTTPError, IOException {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock(new byte[][]{null, "index.html\nindex.css".getBytes()});
    final var publicFiles = new PublicFiles(fileIO, new MimeTypes());
    final var response = (ResponseMock) publicFiles.get(request, new ResponseMock());

    assertEquals("public/", fileIO.getLastGetResourceIfExistsFileName());

    assertNull(response.getSetJsonBodyArg);

    assertEquals("index.html\nindex.css", new String(response.getSetBinaryBodyArg));
    final var headers = response.headers;
  }

  @Test
  void getDirectoryReturns404IfFileNotFound() {
    final var request = new RequestMock("GET", "/index.html");

    final var fileIO = new FileIOMock((byte[]) null);
    final var publicFiles = new PublicFiles(fileIO, new MimeTypes());

    HTTPError exception = assertThrows(HTTPError.class, () -> publicFiles.get(request, new ResponseMock()));

    assertEquals("Not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void getDirectoryReturns404IfDirectoryNotFound() {
    final var request = new RequestMock("GET", "/");

    final var fileIO = new FileIOMock((byte[]) null);
    final var publicFiles = new PublicFiles(fileIO, new MimeTypes());

    HTTPError exception = assertThrows(HTTPError.class, () -> publicFiles.get(request, new ResponseMock()));

    assertEquals("Not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}