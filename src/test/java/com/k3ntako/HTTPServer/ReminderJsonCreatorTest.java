package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.utilities.ReminderJsonCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReminderJsonCreatorTest {

  @Test
  void createReminderList() {
    var reminderList = ReminderJsonCreator.createReminderList("1-2-3-4");

    assertEquals("1-2-3-4", reminderList.get("id").getAsString());

    var reminders = reminderList.getAsJsonObject("items");
    assertNotNull(reminders);
    assertEquals(0, reminders.size());
  }

  @Test
  void createReminder() {
    var reminderList = ReminderJsonCreator.createReminder("1-2-3-4", "Go count");

    assertEquals("1-2-3-4", reminderList.get("id").getAsString());
    assertEquals("Go count", reminderList.get("task").getAsString());
  }
}