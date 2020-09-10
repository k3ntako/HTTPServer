package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReminderListsTest {
  @Test
  void post() throws IOException, HTTPError {
    var request = new RequestMock("POST", "/reminders", "HTTP/1.1", new HashMap<>(), "");
    var reminderIOMock = new ReminderIOMock();

    var reminderLists = new ReminderLists(reminderIOMock);
    var response = reminderLists.post(request);

    assertTrue(reminderIOMock.createNewListCalled);

    var expectedResponse = "HTTP/1.1 200 OK\r\n" +
        "Content-Length: 36\r\n\r\n" +
        "{\"id\":\"mock-new-list-id\",\"items\":{}}";
    assertEquals(expectedResponse, response.createResponse());
  }
}