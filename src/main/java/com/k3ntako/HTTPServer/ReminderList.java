package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class ReminderList {
  public String id;
  public HashMap<String, Reminder> items = new HashMap<>();

  public ReminderList(String id) {
    this.id = id;
  }
}
