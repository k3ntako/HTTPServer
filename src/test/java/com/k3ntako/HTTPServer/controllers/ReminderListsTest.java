package com.k3ntako.HTTPServer.controllers;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.mocks.ReminderIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReminderListsTest {
  @Test
  void post() throws IOException {
    var request = new RequestMock("POST", "/reminders", "HTTP/1.1", new HashMap<>(), "");
    var reminderIOMock = new ReminderIOMock();

    var reminderLists = new ReminderLists(reminderIOMock);
    var response = (ResponseMock) reminderLists.post(request, new ResponseMock());

    assertTrue(reminderIOMock.createNewListCalled);
    assertNull(response.setBodyArg);

    var responseJson = (JsonObject) response.setJsonBodyArg;
    assertEquals(responseJson.get("id").getAsString(), "mock-new-list-id");
    assertNotNull(responseJson.getAsJsonObject("items"));
    assertEquals(responseJson.getAsJsonObject("items").size(), 0);
  }
}