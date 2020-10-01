package com.k3ntako.HTTPServer.controllers;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ImagesTest {

  @Test
  void post() throws IOException, HTTPError {
    final var bytes = "This is the body".getBytes();
    var request = new RequestMock("POST", "/api/images", bytes);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    final var uuidMock = new UUIDMock();


    var images = new Images(dataDirectoryIO, uuidMock);
    var response = (ResponseMock) images.post(request, new ResponseMock());

    var expected = "./mock/data/images/" + uuidMock.getDefaultUUID() + ".png";
    assertEquals(expected, fileIO.getLastWritePath().toString());
    assertArrayEquals(bytes, (byte[]) fileIO.getLastWrite());

    var responseJson = (JsonObject) response.getSetJsonBodyArg;
    var id = responseJson.get("id").getAsString();

    assertEquals(uuidMock.getDefaultUUID(), id);
  }

  @Test
  void get() throws IOException, HTTPError {
    var request = new RequestMock("GET", "/api/images/mock-name.png");

    var routeParams = new HashMap<String, String>();
    routeParams.put("image_name", "mock-name.png");
    request.setRouteParams(routeParams);

    final var imageBytes = "This is the body".getBytes();
    final var fileIO = new FileIOMock(imageBytes);
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock());
    var response = (ResponseMock) images.get(request, new ResponseMock());

    var expected = "./mock/data/images/mock-name.png";
    assertEquals(expected, fileIO.getLastReadPath().toString());

    var responseBytes = response.getSetBinaryBodyArg;
    assertArrayEquals(imageBytes, responseBytes);

    assertEquals("image/png", response.headers.get("Content-Type"));
  }

  @Test
  void getReturns404IfNotFound() {
    final var request = new RequestMock("GET", "/api/images/mock-name.png");

    final var routeParams = new HashMap<String, String>();
    routeParams.put("image_name", "mock-name.png");
    request.setRouteParams(routeParams);

    final var fileIO = new FileIOMock((byte[]) null);
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock());

    HTTPError exception = assertThrows(HTTPError.class, () -> images.get(request, new ResponseMock()));
    assertEquals("Image was not found.", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void delete() throws HTTPError {
    final var request = new RequestMock("DELETE", "/api/images/mock-name.png");

    final var routeParams = new HashMap<String, String>();
    routeParams.put("image_name", "mock-name.png");
    request.setRouteParams(routeParams);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock());
    var response = (ResponseMock) images.delete(request, new ResponseMock());

    var expected = "./mock/data/images/mock-name.png";
    assertEquals(expected, fileIO.getLastDeletePath().toString());
    assertTrue(response.isBodyNull());
  }

  @Test
  void deleteReturns404IfNotFound() {
    final var request = new RequestMock("DELETE", "/api/images/mock-name.png");

    final var routeParams = new HashMap<String, String>();
    routeParams.put("image_name", "mock-name.png");
    request.setRouteParams(routeParams);

    final var fileIO = new FileIOMock(new IOException("File does not exist"));
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock());

    HTTPError exception = assertThrows(HTTPError.class, () -> images.delete(request, new ResponseMock()));
    assertEquals("Image was not found.", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}