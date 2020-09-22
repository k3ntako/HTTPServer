package com.k3ntako.HTTPServer;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

public interface ReminderIOInterface {
  JsonObject createNewList() throws IOException;

  JsonObject getReminderByIds(String listId, String reminderId) throws IOException, HTTPError;

  JsonObject addReminder(String listId, String task) throws IOException, HTTPError;

  JsonObject updateReminder(String listId, String reminderId, String updatedTask) throws IOException, HTTPError;

  void deleteReminder(String listId, String reminderId) throws IOException, HTTPError;
}
