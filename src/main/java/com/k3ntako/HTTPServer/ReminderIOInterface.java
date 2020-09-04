package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface ReminderIOInterface {
  Reminder getById(String id) throws IOException;

  String addNew(String task) throws IOException;

  void overwrite(String id, String task) throws IOException;

  void delete(String id) throws IOException;
}
