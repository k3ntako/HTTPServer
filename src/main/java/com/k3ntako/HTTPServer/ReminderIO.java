package com.k3ntako.HTTPServer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class ReminderIO implements ReminderIOInterface {
  final private DataDirectoryIO dataDirectoryIO;
  final private JsonConverterInterface jsonConverter;
  final private UUIDInterface uuid;
  final private String remindersDir = "/reminders/";

  public ReminderIO(DataDirectoryIO dataDirectoryIO, JsonConverterInterface jsonConverter, UUIDInterface uuid) {
    this.dataDirectoryIO = dataDirectoryIO;
    this.jsonConverter = jsonConverter;
    this.uuid = uuid;
  }

  public JsonObject createNewList() throws IOException {
    var uuid = this.uuid.generate();

    var reminderList = ReminderJsonCreator.createReminderList(uuid);

    var filePath = this.generatePath(uuid);
    var fileStr = jsonConverter.toJson(reminderList);
    dataDirectoryIO.write(filePath, fileStr);

    return reminderList;
  }

  public JsonObject getReminderByIds(String listId, String reminderId) throws IOException, HTTPError {
    var reminderList = this.getListById(listId);
    return getReminderById(reminderList, reminderId);
  }

  public JsonObject addReminder(String listId, String task) throws IOException, HTTPError {
    var reminderList = getListById(listId);

    var reminder = ReminderJsonCreator.createReminder(uuid.generate(), task);
    reminderList.getAsJsonObject("items").add(reminder.get("id").getAsString(), reminder);

    writeToFile(listId, reminderList);

    return reminder;
  }

  public JsonObject updateReminder(String listId, String reminderId, String updatedTask) throws IOException, HTTPError {
    var reminderList = getListById(listId);
    var reminder = getReminderById(reminderList, reminderId);

    reminder.addProperty("task", updatedTask);
    reminderList.getAsJsonObject("items").add(reminderId, reminder);

    writeToFile(listId, reminderList);

    return reminder;
  }

  public void deleteReminder(String listId, String reminderId) throws IOException, HTTPError {
    var reminderList = getListById(listId);

    var removedReminder = reminderList.getAsJsonObject("items").remove(reminderId);

    if (removedReminder == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    writeToFile(listId, reminderList);
  }

  private String generatePath(String id) {
    return remindersDir + id + ".json";
  }

  private JsonObject getListById(String id) throws IOException, HTTPError {
    var filePath = generatePath(id);

    var jsonStr = dataDirectoryIO.readString(filePath);

    if (jsonStr == null) {
      throw new HTTPError(404, "Reminder list was not found");
    }

    return (JsonObject) JsonParser.parseString(jsonStr);
  }

  private JsonObject getReminderById(JsonObject reminderList, String reminderId) throws HTTPError {
    var items = (JsonObject) reminderList.get("items");
    var reminder = (JsonObject) items.get(reminderId);

    if (reminder == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    return reminder;
  }

  private void writeToFile(String listId, JsonObject reminderList) throws IOException {
    var filePath = this.generatePath(listId);
    var fileStr = jsonConverter.toJson(reminderList); // might be an issue
    dataDirectoryIO.write(filePath, fileStr);
  }
}
