package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.TextFile;
import com.k3ntako.HTTPServer.UUID;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RemindersTest {

  @Test
  void post() throws IOException, HTTPError {
    var postBody = "hello post!";
    var request = new RequestMock("POST", "/reminders", "HTTP/1.1", new HashMap<>(), postBody);
    var fileIOMock = new FileIOMock();

    var textFile = new TextFile(fileIOMock, new UUIDMock());
    var reminders = new Reminders(textFile);
    reminders.post(request);

    assertEquals(postBody, fileIOMock.getLastWrite());
    assertEquals("./data/8d142d80-565f-417d-8334-a8a19caadadb.txt", fileIOMock.getLastWritePath().toString());
  }

  @Test
  void postReturnsFileName() throws IOException, HTTPError {
    var postBody = "hello post!";
    var request = new RequestMock("POST", "/reminders", "HTTP/1.1", new HashMap<>(), postBody);

    var textFile = new TextFile(new FileIOMock(), new UUIDMock());
    var reminders = new Reminders(textFile);
    var response = reminders.post(request);
    var responseStr = response.createResponse();

    var expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Length: 36\r\n\r\n" + "8d142d80-565f-417d-8334-a8a19caadadb";

    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void postThrowsErrorIfBodyIsMultipleLines() {
    var postBody = "hello post!\nsecond line";
    var request = new RequestMock("POST", "/reminders", "HTTP/1.1", new HashMap<>(), postBody);

    var textFile = new TextFile(new FileIOMock(), new UUIDMock());
    var reminders = new Reminders(textFile);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.post(request));

    assertEquals("Request body should not be multiline", exception.getMessage());
    assertEquals(400, exception.getStatus());
  }

  @Test
  void get() throws IOException, HTTPError {
    var mockUUID = new UUIDMock();

    var content = "text file content!";
    var request = new RequestMock("GET", "/reminders/" + mockUUID.getDefaultUUID());

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", mockUUID.getDefaultUUID());
    request.setRouteParams(routeParams);

    var fileIOMock = new FileIOMock(content);

    var textFile = new TextFile(fileIOMock, mockUUID);
    var reminders = new Reminders(textFile);
    var response = reminders.get(request);

    assertEquals("./data/" + mockUUID.getDefaultUUID() + ".txt", fileIOMock.getLastReadPath().toString());

    var expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Length: 18\r\n\r\n" + content;

    assertEquals(expectedResponse, response.createResponse());
  }


  @Test
  void getThrows404IfFileIsNotFound() {
    var request = new RequestMock("GET", "/reminders/not-an-id");

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", "not-an-id");
    request.setRouteParams(routeParams);

    var fileIOMock = new FileIOMock((String) null);

    var textFile = new TextFile(fileIOMock, new UUID());
    var reminders = new Reminders(textFile);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.get(request));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void patch() throws HTTPError {
    var mockUUID = new UUIDMock();

    var content = "text file content!";
    var request = new RequestMock("PATCH", "/reminders/" + mockUUID.getDefaultUUID(), content);

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", mockUUID.getDefaultUUID());
    request.setRouteParams(routeParams);

    var fileIOMock = new FileIOMock();

    var textFile = new TextFile(fileIOMock, mockUUID);
    var reminders = new Reminders(textFile);
    var response = reminders.patch(request);

    assertEquals("./data/" + mockUUID.getDefaultUUID() + ".txt", fileIOMock.getLastPatchPath().toString());
    assertEquals(content, fileIOMock.getLastPatch());

    var expectedResponse = "HTTP/1.1 204 No Content\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void patchReturns404IfFileNotFound() {
    var request = new RequestMock("PATCH", "/reminders/not-an-id");

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", "not-an-id");
    request.setRouteParams(routeParams);

    var fileIOMock = new FileIOMock(new IOException("File does not exist"));

    var textFile = new TextFile(fileIOMock, new UUID());
    var reminders = new Reminders(textFile);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.patch(request));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void patchThrowsErrorIfBodyIsMultipleLines() {
    var mockUUID = new UUIDMock();
    var patchBody = "hello post!\nsecond line";
    var request = new RequestMock("PATCH", "/reminders", patchBody);

    var routeParams = new HashMap<String, String>();
    routeParams.put("id", mockUUID.getDefaultUUID());
    request.setRouteParams(routeParams);

    var textFile = new TextFile(new FileIOMock(), mockUUID);
    var reminders = new Reminders(textFile);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.patch(request));

    assertEquals("Request body should not be multiline", exception.getMessage());
    assertEquals(400, exception.getStatus());
  }

  @Test
  void put() throws HTTPError {
    var mockUUID = new UUIDMock();

    var content = "text file content!";
    var request = new RequestMock("PUT", "/reminders/" + mockUUID.getDefaultUUID(), "Hello world");

    var params = new HashMap<String, String>();
    params.put("id", mockUUID.getDefaultUUID());
    request.setParams(params);

    var fileIOMock = new FileIOMock(content);

    var textFile = new TextFile(fileIOMock, mockUUID);
    var reminders = new Reminders(textFile);
    var response = reminders.put(request);

    assertEquals("./data/" + mockUUID.getDefaultUUID() + ".txt", fileIOMock.getLastOverwritePath().toString());

    var expectedResponse = "HTTP/1.1 204 No Content\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void putReturns404IfFileNotFound() {
    var request = new RequestMock("PUT", "/reminders/not-an-id");

    var params = new HashMap<String, String>();
    params.put("id", "not-an-id");
    request.setParams(params);

    var fileIOMock = new FileIOMock(new IOException("File does not exist"));

    var textFile = new TextFile(fileIOMock, new UUID());
    var reminders = new Reminders(textFile);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.put(request));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void putThrowsErrorIfBodyIsMultipleLines() {
    var mockUUID = new UUIDMock();
    var putBody = "hello post!\nsecond line";
    var request = new RequestMock("PUT", "/reminders", putBody);

    var params = new HashMap<String, String>();
    params.put("id", mockUUID.getDefaultUUID());
    request.setParams(params);

    var textFile = new TextFile(new FileIOMock(), mockUUID);
    var reminders = new Reminders(textFile);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.put(request));

    assertEquals("Request body should not be multiline", exception.getMessage());
    assertEquals(400, exception.getStatus());
  }
}