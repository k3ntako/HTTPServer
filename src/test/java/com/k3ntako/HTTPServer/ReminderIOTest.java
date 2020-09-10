package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReminderIOTest {

  @Test
  void getReminderByIds() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\":{\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data/");

    var reminder = reminderIO.getReminderByIds("reminder-list-1", "reminder-123");

    assertNotNull(reminder);
    assertEquals("reminder-123", reminder.id);
    assertEquals("task content!!", reminder.task);
    assertEquals("./mock/data/reminder-list-1.json", fileIO.getLastReadPath().toString());
  }

  @Test
  void createNewList() throws IOException {
    var fileIO = new FileIOMock();
    var jsonIO = new JsonIO(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(fileIO, jsonIO, uuid, "./mock/data/");

    var returnedReminderList = reminderIO.createNewList();

    var expectedPath = "./mock/data/" + uuid.getDefaultUUID() + ".json";
    assertEquals(expectedPath, fileIO.getLastWritePath().toString());

    var expectedWrite = "{" +
        "\"id\":\"" + uuid.getDefaultUUID() + "\"," +
        "\"items\":{}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());

    assertEquals(uuid.getDefaultUUID(), returnedReminderList.id);
    assertEquals(0, returnedReminderList.items.size());
  }

  @Test
  void addReminder() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(fileIO, jsonIO, uuid, "./mock/data/");

    var reminder = reminderIO.addReminder("reminder-list-1", "Do this task!");

    var expectedPath = "./mock/data/reminder-list-1.json";
    assertEquals(expectedPath, fileIO.getLastReadPath().toString());

    var expectedWrite = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"" + uuid.getDefaultUUID() + "\":" +
        "{" +
        "\"id\":\"" + uuid.getDefaultUUID() + "\"," +
        "\"task\":\"Do this task!\"" +
        "}" +
        "}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());

    assertEquals(uuid.getDefaultUUID(), reminder.id);
    assertEquals("Do this task!", reminder.task);
  }

  @Test
  void updateReminder() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-1\":" +
        "{" +
        "\"id\":\"reminder-1\"," +
        "\"task\":\"Do this task!\"" +
        "}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(fileIO, jsonIO, uuid, "./mock/data/");

    var reminder = reminderIO.updateReminder("reminder-list-1", "reminder-1", "Updated task!");

    var expectedPath = "./mock/data/reminder-list-1.json";
    assertEquals(expectedPath, fileIO.getLastReadPath().toString());

    var expectedWrite = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-1\":" +
        "{" +
        "\"id\":\"reminder-1\"," +
        "\"task\":\"Updated task!\"" +
        "}" +
        "}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());

    assertEquals("reminder-1", reminder.id);
    assertEquals("Updated task!", reminder.task);
  }

  @Test
  void deleteReminder() throws IOException {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-1\":" +
        "{" +
        "\"id\":\"reminder-1\"," +
        "\"task\":\"Do this task!\"" +
        "}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var jsonIO = new JsonIO(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(fileIO, jsonIO, uuid, "./mock/data/");

    reminderIO.deleteReminder("reminder-list-1", "reminder-1");

    var expectedPath = "./mock/data/reminder-list-1.json";
    assertEquals(expectedPath, fileIO.getLastReadPath().toString());

    var expectedWrite = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());
  }

//  @Test
//  void getById() throws IOException {
//    var mockJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\": {" +
//        "\"reminder-123\": {\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
//        "}" +
//        "}";
//
//    var fileIO = new FileIOMock(mockJson);
//    var jsonIO = new JsonIO(new Gson());
//    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data.json");
//
//    var reminder = reminderIO.getById("reminder-123");
//
//    assertEquals(
//        "./mock/data.json",
//        fileIO.getLastReadPath().toString()
//    );
//
//    assertEquals("reminder-123", reminder.id);
//    assertEquals("task content!!", reminder.task);
//  }

//  @Test
//  void write() throws IOException {
//    var mockJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\": {" +
//        "\"reminder-123\": {\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
//        "}" +
//        "}";
//
//
//    var fileIO = new FileIOMock(mockJson);
//    var jsonIO = new JsonIO(new Gson());
//    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUIDMock("reminder-809"), "./mock/data.json");
//
//    var reminderId = reminderIO.addNew("new task");
//    assertEquals("reminder-809", reminderId);
//
//    assertEquals(
//        "./mock/data.json",
//        fileIO.getLastWritePath().toString()
//    );
//
//    var expectedJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\":{" +
//        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
//        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"task content!!\"}" +
//        "}" +
//        "}";
//
//    assertEquals(
//        expectedJson,
//        fileIO.getLastWrite()
//    );
//  }

//  @Test
//  void overwrite() throws IOException {
//    var mockJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\":{" +
//        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
//        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"task content!!\"}" +
//        "}" +
//        "}";
//
//    var fileIO = new FileIOMock(mockJson);
//    var jsonIO = new JsonIO(new Gson());
//    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data.json");
//
//    reminderIO.overwrite("reminder-123", "overwrite content");
//
//    var expectedJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\":{" +
//        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
//        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"overwrite content\"}" +
//        "}" +
//        "}";
//
//    assertEquals(
//        expectedJson,
//        fileIO.getLastWrite()
//    );
//  }

//  @Test
//  void delete() throws IOException {
//    var mockJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\":{" +
//        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}," +
//        "\"reminder-123\":{\"id\":\"reminder-123\",\"task\":\"task content!!\"}" +
//        "}" +
//        "}";
//
//    var fileIO = new FileIOMock(mockJson);
//    var jsonIO = new JsonIO(new Gson());
//    var reminderIO = new ReminderIO(fileIO, jsonIO, new UUID(), "./mock/data.json");
//
//    reminderIO.delete("reminder-123");
//
//    var expectedJson = "{" +
//        "\"id\":\"reminder-list-1\"," +
//        "\"items\":{" +
//        "\"reminder-809\":{\"id\":\"reminder-809\",\"task\":\"new task\"}" +
//        "}" +
//        "}";
//
//    assertEquals(
//        expectedJson,
//        fileIO.getLastWrite()
//    );
//  }
}
