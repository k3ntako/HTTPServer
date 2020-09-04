package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class ReminderFile {
  private String id;
  public HashMap<String, Reminder> reminders = new HashMap<>();

  public ReminderFile(String id) {
    this.id = id;
  }
}
