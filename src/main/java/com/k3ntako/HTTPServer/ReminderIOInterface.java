package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface ReminderIOInterface {
  ReminderList createNewList() throws IOException;

  Reminder getReminderByIds(String listId, String reminderId) throws IOException, HTTPError;

  Reminder addReminder(String listId, String task) throws IOException, HTTPError;

  Reminder updateReminder(String listId, String reminderId, String updatedTask) throws IOException, HTTPError;

  void deleteReminder(String listId, String reminderId) throws IOException, HTTPError;
}
