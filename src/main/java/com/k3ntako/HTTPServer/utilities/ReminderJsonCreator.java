package com.k3ntako.HTTPServer.utilities;

import com.google.gson.JsonObject;

public class ReminderJsonCreator {
  static public JsonObject createReminderList(String id) {
    var reminderList = new JsonObject();
    reminderList.addProperty("id", id);
    reminderList.add("items", new JsonObject());
    return reminderList;
  }

  static public JsonObject createReminder(String id, String task) {
    var reminder = new JsonObject();
    reminder.addProperty("id", id);
    reminder.addProperty("task", task);
    return reminder;
  }
}
