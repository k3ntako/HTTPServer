package com.k3ntako.HTTPServer.fileSystemsIO;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.HTTPError;

import java.io.IOException;

public interface ReminderIOInterface {
  JsonObject createNewList() throws IOException;

  JsonObject getReminderByIds(String listId, String reminderId) throws IOException, HTTPError;

  JsonObject addReminder(String listId, String task) throws IOException, HTTPError;

  JsonObject updateReminder(String listId, String reminderId, String updatedTask) throws IOException, HTTPError;

  void deleteReminder(String listId, String reminderId) throws IOException, HTTPError;
}
