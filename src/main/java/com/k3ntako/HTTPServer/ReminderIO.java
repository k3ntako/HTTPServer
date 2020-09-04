package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ReminderIO implements ReminderIOInterface {
  private JsonIOInterface jsonIO;
  private FileIOInterface fileIO;
  private Path filePath;
  private UUIDInterface uuid;

  public ReminderIO(FileIOInterface fileIO, JsonIOInterface jsonIO, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.jsonIO = jsonIO;
    this.uuid = uuid;

    var strPath = "./data/reminders.json";
    this.filePath = FileSystems.getDefault().getPath(strPath);
  }

  @Override
  public Reminder getById(String id) throws IOException {
    var reminderFile = all();
    return reminderFile.reminders.get(id);
  }

  @Override
  public String write(String task) throws IOException {
    var reminder = new Reminder(uuid.generate(), task);
    var reminderFile = all();

    reminderFile.reminders.put(reminder.id, reminder);

    var fileStr = jsonIO.toJson(reminderFile);
    fileIO.write(filePath, fileStr);

    return reminder.id;
  }

  private ReminderFile all() throws IOException {
    var jsonStr = fileIO.read(filePath);
    var reminderFile = jsonIO.fromJson(jsonStr, ReminderFile.class);

    if (reminderFile == null) {
      reminderFile = new ReminderFile(uuid.generate());
    }

    return reminderFile;
  }

}
