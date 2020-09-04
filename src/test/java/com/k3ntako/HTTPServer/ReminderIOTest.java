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
        "\"reminders\": {" +
        "\"123\": {\"id\": \"123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID());

    var reminder = reminderIO.getById("123");

    assertEquals(
        "./data/reminders.json",
        fileIO.getLastReadPath().toString()
    );

    assertEquals("123", reminder.id);
    assertEquals("task content!!", reminder.task);
  }

  @Test
  void write() throws IOException {
    var mockJson = "{" +
        "\"reminders\": {" +
        "\"123\": {\"id\": \"123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";


    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUIDMock("809"));

    var reminderId = reminderIO.addNew("new task");
    assertEquals("809", reminderId);

    assertEquals(
        "./data/reminders.json",
        fileIO.getLastWritePath().toString()
    );

    var expectedJson = "{" +
        "\"reminders\":{" +
        "\"809\":{\"id\":\"809\",\"task\":\"new task\"}," +
        "\"123\":{\"id\":\"123\",\"task\":\"task content!!\"}" +
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
        "\"reminders\":{" +
        "\"809\":{\"id\":\"809\",\"task\":\"new task\"}," +
        "\"123\":{\"id\":\"123\",\"task\":\"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID());

    reminderIO.overwrite("123", "overwrite content");

    var expectedJson = "{" +
        "\"reminders\":{" +
        "\"809\":{\"id\":\"809\",\"task\":\"new task\"}," +
        "\"123\":{\"id\":\"123\",\"task\":\"overwrite content\"}" +
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
        "\"reminders\":{" +
        "\"809\":{\"id\":\"809\",\"task\":\"new task\"}," +
        "\"123\":{\"id\":\"123\",\"task\":\"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID());

    reminderIO.delete("123");

    var expectedJson = "{" +
        "\"reminders\":{" +
        "\"809\":{\"id\":\"809\",\"task\":\"new task\"}" +
        "}" +
        "}";

    assertEquals(
        expectedJson,
        fileIO.getLastWrite()
    );
  }
}
