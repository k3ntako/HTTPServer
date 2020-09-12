package com.k3ntako.HTTPServer;

public class Reminder {
  public String id;
  public String task;

  public Reminder(String uuid, String task) {
    this.task = task;
    this.id = uuid;
  }
}
