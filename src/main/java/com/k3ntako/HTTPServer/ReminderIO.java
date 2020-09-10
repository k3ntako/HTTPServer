package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ReminderIO implements ReminderIOInterface {
  private JsonIOInterface jsonIO;
  private FileIOInterface fileIO;
  private Path filePath;
  private UUIDInterface uuid;

  public ReminderIO(FileIOInterface fileIO, JsonIOInterface jsonIO, UUIDInterface uuid, String dataDir) {
    this.fileIO = fileIO;
    this.jsonIO = jsonIO;
    this.uuid = uuid;

    this.filePath = FileSystems.getDefault().getPath(dataDir);
  }

  @Override
  public Reminder getById(String id) throws IOException {
    var reminderList = all();
    return reminderList.items.get(id);
  }

  @Override
  public String addNew(String task) throws IOException {
    var reminder = new Reminder(uuid.generate(), task);
    var reminderList = all();

    reminderList.items.put(reminder.id, reminder);

    this.writeToFile(reminderList);

    return reminder.id;
  }

  @Override
  public void overwrite(String id, String task) throws IOException {
    var reminderList = all();
    var reminder = reminderList.items.get(id);
    reminderList.items.remove(id);

    reminder.task = task;
    reminderList.items.put(id, reminder);

    this.writeToFile(reminderList);
  }

  @Override
  public void delete(String id) throws IOException {
    var reminderList = all();
    reminderList.items.remove(id);

    this.writeToFile(reminderList);
  }

  private void writeToFile(ReminderList reminderList) throws IOException {
    var fileStr = jsonIO.toJson(reminderList);
    fileIO.write(filePath, fileStr);
  }


  private ReminderList all() throws IOException {
    var jsonStr = fileIO.read(filePath);

    var reminderList = jsonIO.fromJson(jsonStr, ReminderList.class);
    
    if (reminderList == null) {
      reminderList = new ReminderList(uuid.generate());
    }

    return reminderList;
  }
}
