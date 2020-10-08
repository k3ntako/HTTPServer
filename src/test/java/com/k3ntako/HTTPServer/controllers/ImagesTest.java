package com.k3ntako.HTTPServer.controllers;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import com.k3ntako.HTTPServer.utilities.FileExtensions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ImagesTest {

  @Test
  void postPng() throws IOException, HTTPError {
    var headers = new HashMap<String, String>();
    headers.put("Content-Type", "image/png");
    final var bytes = "This is the body".getBytes();
    var request = new RequestMock("POST", "/api/images", "HTTP/1.1", headers, bytes);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    final var uuidMock = new UUIDMock();


    var images = new Images(dataDirectoryIO, uuidMock, new FileExtensions());
    var response = (ResponseMock) images.post(request, new ResponseMock());

    var expected = "./mock/data/images/" + uuidMock.getDefaultUUID() + ".png";
    assertEquals(expected, fileIO.getLastWritePath().toString());
    assertArrayEquals(bytes, (byte[]) fileIO.getLastWrite());

    var responseJson = (JsonObject) response.getSetJsonBodyArg;
    var id = responseJson.get("id").getAsString();

    assertEquals(uuidMock.getDefaultUUID(), id);
  }

  @Test
  void postJpg() throws IOException, HTTPError {
    final var bytes = "This is the body".getBytes();

    var headers = new HashMap<String, String>();
    headers.put("Content-Type", "image/jpeg");

    var request = new RequestMock("POST", "/api/images", "HTTP/1.1", headers, bytes);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    final var uuidMock = new UUIDMock();

    var images = new Images(dataDirectoryIO, uuidMock, new FileExtensions());
    var response = (ResponseMock) images.post(request, new ResponseMock());

    var expected = "./mock/data/images/" + uuidMock.getDefaultUUID() + ".jpg";
    assertEquals(expected, fileIO.getLastWritePath().toString());

    var responseJson = (JsonObject) response.getSetJsonBodyArg;
    var id = responseJson.get("id").getAsString();

    assertEquals(uuidMock.getDefaultUUID(), id);
  }

  @Test
  void postThrowsErrorIfExtensionNotFound() {
    final var bytes = "This is the body".getBytes();

    var headers = new HashMap<String, String>();
    headers.put("Content-Type", "fake/type");

    var request = new RequestMock("POST", "/api/images", "HTTP/1.1", headers, bytes);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    final var uuidMock = new UUIDMock();

    var images = new Images(dataDirectoryIO, uuidMock, new FileExtensions());

    HTTPError exception = assertThrows(HTTPError.class, () -> images.post(request, new ResponseMock()));
    assertEquals("Invalid image type", exception.getMessage());
    assertEquals(400, exception.getStatus());
  }

  @Test
  void get() throws IOException, HTTPError {
    var request = new RequestMock("GET", "/api/images/mock-name");

    var routeParams = new HashMap<String, String>();
    routeParams.put("image_id", "mock-name");
    request.setRouteParams(routeParams);

    final var imageBytes = "This is the body".getBytes();
    final var imageFiles = new File[]{new File("1.jpg"), new File("mock-name.png")};
    final var fileIO = new FileIOMock(imageBytes, imageFiles);
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock(), new FileExtensions());
    var response = (ResponseMock) images.get(request, new ResponseMock());

    var expected = "mock-name.png";
    assertEquals(expected, fileIO.getLastReadPath().toString());

    var responseBytes = response.getSetBinaryBodyArg;
    assertArrayEquals(imageBytes, responseBytes);
  }

  @Test
  void getReturns404IfNotFound() {
    final var request = new RequestMock("GET", "/api/images/mock-name.png");

    final var routeParams = new HashMap<String, String>();
    routeParams.put("image_id", "mock-name.png");
    request.setRouteParams(routeParams);

    final var fileIO = new FileIOMock((byte[]) null);
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock(), new FileExtensions());

    HTTPError exception = assertThrows(HTTPError.class, () -> images.get(request, new ResponseMock()));
    assertEquals("Image was not found.", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void delete() throws HTTPError {
    final var request = new RequestMock("DELETE", "/api/images/mock-name.png");

    final var routeParams = new HashMap<String, String>();
    routeParams.put("image_id", "mock-name.png");
    request.setRouteParams(routeParams);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock(), new FileExtensions());
    var response = (ResponseMock) images.delete(request, new ResponseMock());

    var expected = "./mock/data/images/mock-name.png";
    assertEquals(expected, fileIO.getLastDeletePath().toString());
    assertTrue(response.isBodyNull());
  }

  @Test
  void deleteReturns404IfNotFound() {
    final var request = new RequestMock("DELETE", "/api/images/mock-name.png");

    final var routeParams = new HashMap<String, String>();
    routeParams.put("image_id", "mock-name.png");
    request.setRouteParams(routeParams);

    final var fileIO = new FileIOMock(new IOException("File does not exist"));
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var images = new Images(dataDirectoryIO, new UUIDMock(), new FileExtensions());

    HTTPError exception = assertThrows(HTTPError.class, () -> images.delete(request, new ResponseMock()));
    assertEquals("Image was not found.", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}