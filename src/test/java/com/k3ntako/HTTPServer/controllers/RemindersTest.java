package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
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
    var request = new RequestMock("POST", "/reminders/reminder-list-123", "HTTP/1.1", new HashMap<>(), postBody);
    var routeParams = new HashMap<String, String>();
    routeParams.put("list_id", "reminder-list-123");
    request.setRouteParams(routeParams);

    var reminderIOMock = new ReminderIOMock();

    var reminders = new Reminders(reminderIOMock);
    reminders.post(request);

    assertEquals("reminder-list-123", reminderIOMock.listIdForWrite);
    assertEquals(postBody, reminderIOMock.taskForWrite);
  }

  @Test
  void postReturnsReminderJson() throws IOException, HTTPError {
    var postBody = "hello post!";
    var request = new RequestMock("POST", "/reminders/reminder-list-123", "HTTP/1.1", new HashMap<>(), postBody);
    var routeParams = new HashMap<String, String>();
    routeParams.put("list_id", "reminder-list-123");
    request.setRouteParams(routeParams);

    var reminderIOMock = new ReminderIOMock();
    var reminders = new Reminders(reminderIOMock);
    var response = reminders.post(request);
    var responseStr = response.createResponse();

    var expectedResponse = "HTTP/1.1 200 OK\r\n" + "Content-Length: 85\r\n\r\n" +
        "{\"" +
        "id\":\"8d142d80-565f-417d-8334-a8a19caadadb\"," +
        "\"task\":\"ReminderIOMock: mock task text\"" +
        "}";

    assertEquals(expectedResponse, responseStr);
  }

  @Test
  void postThrowsErrorIfBodyIsMultipleLines() {
    var postBody = "hello post!\nsecond line";
    var request = new RequestMock("POST", "/reminders/reminder-list-123", "HTTP/1.1", new HashMap<>(), postBody);
    var routeParams = new HashMap<String, String>();
    routeParams.put("list_id", "reminder-list-123");
    request.setRouteParams(routeParams);

    var reminderIOMock = new ReminderIOMock();
    var reminders = new Reminders(reminderIOMock);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.post(request));

    assertEquals("Request body should not be multiline", exception.getMessage());
    assertEquals(400, exception.getStatus());
  }

  @Test
  void get() throws IOException, HTTPError {
    var mockUUID = new UUIDMock();

    var request = new RequestMock("GET", "/reminders/reminder-list-123/" + mockUUID.getDefaultUUID());

    var routeParams = new HashMap<String, String>();
    routeParams.put("list_id", "reminder-list-123");
    routeParams.put("reminder_id", mockUUID.getDefaultUUID());
    request.setRouteParams(routeParams);

    var reminderIOMock = new ReminderIOMock();
    var reminders = new Reminders(reminderIOMock);
    var response = reminders.get(request);

    assertEquals(mockUUID.getDefaultUUID(), reminderIOMock.reminderIdForGet);

    var expectedResponse =
        "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 85\r\n\r\n" +
            "{\"id\":\"" +
            mockUUID.getDefaultUUID() +
            "\",\"task\":\"ReminderIOMock: mock task text\"}";

    assertEquals(expectedResponse, response.createResponse());
  }


  @Test
  void getThrows404IfFileIsNotFound() {
    var request = new RequestMock("GET", "/reminders/reminder-list-123/not-an-id");

    var routeParams = new HashMap<String, String>();
    routeParams.put("list_id", "reminder-list-123");
    routeParams.put("reminder_id", "not-an-id");
    request.setRouteParams(routeParams);

    var reminderIOMock = new ReminderIOMock((Reminder) null);
    var reminders = new Reminders(reminderIOMock);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.get(request));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void put() throws HTTPError {
    var mockUUID = new UUIDMock();

    var content = "text file content!";
    var request = new RequestMock("PUT", "/reminders/reminder-list-123/" + mockUUID.getDefaultUUID(), content);

    var params = new HashMap<String, String>();
    params.put("list_id", "reminder-list-123");
    params.put("reminder_id", mockUUID.getDefaultUUID());
    request.setRouteParams(params);

    var reminderIOMock = new ReminderIOMock();
    var reminders = new Reminders(reminderIOMock);
    var response = reminders.put(request);

    assertEquals(mockUUID.getDefaultUUID(), reminderIOMock.reminderIdForUpdate);
    assertEquals(content, reminderIOMock.taskForUpdate);

    var expectedResponse = "HTTP/1.1 204 No Content\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void putReturns404IfFileNotFound() {
    var request = new RequestMock("PUT", "/reminders/reminder-list-123/not-an-id");

    var params = new HashMap<String, String>();
    params.put("list_id", "reminder-list-123");
    params.put("reminder_id", "not-an-id");
    request.setRouteParams(params);

    var reminderIOMock = new ReminderIOMock(new IOException("File does not exist"));
    var reminders = new Reminders(reminderIOMock);

    HTTPError exception = assertThrows(HTTPError.class, () -> reminders.put(request));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void putAllowsMultipleLines() throws HTTPError {
    var mockUUID = new UUIDMock();

    var content = "text file content!\nsecond line";
    var request = new RequestMock("PUT", "/reminders/reminder-list-123/" + mockUUID.getDefaultUUID(), content);

    var params = new HashMap<String, String>();
    params.put("list_id", "reminder-list-123");
    params.put("reminder_id", mockUUID.getDefaultUUID());
    request.setRouteParams(params);

    var reminderIOMock = new ReminderIOMock();
    var reminders = new Reminders(reminderIOMock);
    var response = reminders.put(request);

    assertEquals("reminder-list-123", reminderIOMock.listIdForUpdate);
    assertEquals(mockUUID.getDefaultUUID(), reminderIOMock.reminderIdForUpdate);
    assertEquals(content, reminderIOMock.taskForUpdate);

    var expectedResponse = "HTTP/1.1 204 No Content\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void delete() throws HTTPError {
    var mockUUID = new UUIDMock();

    var request = new RequestMock("DELETE", "/reminders/reminder-list-123/" + mockUUID.getDefaultUUID(), "");
    var params = new HashMap<String, String>();
    params.put("list_id", "reminder-list-123");
    params.put("reminder_id", mockUUID.getDefaultUUID());
    request.setRouteParams(params);

    var reminderIOMock = new ReminderIOMock();
    var reminders = new Reminders(reminderIOMock);
    var response = reminders.delete(request);

    assertEquals(mockUUID.getDefaultUUID(), reminderIOMock.reminderIdForDelete);


    var expectedResponse = "HTTP/1.1 204 No Content\r\n" +
        "Content-Length: 0\r\n\r\n";

    assertEquals(expectedResponse, response.createResponse());
  }

  @Test
  void deleteThrowsHTTPErrorIfFileNotFound() {
    var request = new RequestMock("DELETE", "/reminders/reminder-list-123/not-an-id", "");
    var params = new HashMap<String, String>();
    params.put("list_id", "reminder-list-123");
    params.put("reminder_id", "not-an-id");
    request.setRouteParams(params);

    var reminderIOMock = new ReminderIOMock(new IOException("File does not exist"));
    var reminders = new Reminders(reminderIOMock);
    HTTPError exception = assertThrows(
        HTTPError.class,
        () -> reminders.delete(request)
    );

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}