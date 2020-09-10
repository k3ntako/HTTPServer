package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReminderIOTest {

  @Test
  void getById() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\": {\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data.json");

    var reminder = reminderIO.getById("reminder-123");

    assertEquals(
        "./mock/data.json",
        fileIO.getLastReadPath().toString()
    );

    assertEquals("reminder-123", reminder.id);
    assertEquals("task content!!", reminder.task);
  }

  @Test
  void write() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\": {\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";


    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUIDMock("reminder-809"), "./mock/data.json");

    var reminderId = reminderIO.addNew("new task");
    assertEquals("reminder-809", reminderId);

    assertEquals(
        "./mock/data.json",
        fileIO.getLastWritePath().toString()
    );

    var expectedJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"task content!!\"}" +
        "}" +
        "}";

    assertEquals(
        expectedJson,
        fileIO.getLastWrite()
    );
  }

  @Test
  void overwrite() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data.json");

    reminderIO.overwrite("reminder-123", "overwrite content");

    var expectedJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"overwrite content\"}" +
        "}" +
        "}";

    assertEquals(
        expectedJson,
        fileIO.getLastWrite()
    );
  }

  @Test
  void delete() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data.json");

    reminderIO.delete("reminder-123");

    var expectedJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}" +
        "}" +
        "}";

    assertEquals(
        expectedJson,
        fileIO.getLastWrite()
    );
  }
}
