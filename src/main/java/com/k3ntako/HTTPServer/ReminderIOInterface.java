package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface ReminderIOInterface {
  Reminder getById(String id) throws IOException;

  String write(String task) throws IOException;
}
