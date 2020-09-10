package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ReminderIO implements ReminderIOInterface {
  private JsonIOInterface jsonIO;
  private FileIOInterface fileIO;
  private String remindersDir;
  private UUIDInterface uuid;

  public ReminderIO(FileIOInterface fileIO, JsonIOInterface jsonIO, UUIDInterface uuid, String dataDir) {
    this.fileIO = fileIO;
    this.jsonIO = jsonIO;
    this.uuid = uuid;

    this.remindersDir = dataDir;
  }

  public ReminderList createNewList() throws IOException {
    var uuid = this.uuid.generate();

    var reminderList = new ReminderList(uuid);

    var filePath = this.generatePath(uuid);
    var fileStr = jsonIO.toJson(reminderList);
    fileIO.write(filePath, fileStr);

    return reminderList;
  }

  public Reminder getReminderByIds(String listId, String reminderId) throws IOException, HTTPError {
    var reminderList = this.getListById(listId);
    return  getReminderById(reminderList, reminderId);
  }

  public Reminder addReminder(String listId, String task) throws IOException, HTTPError {
    var reminderList = getListById(listId);

    var reminder = new Reminder(uuid.generate(), task);
    reminderList.items.put(reminder.id, reminder);

    writeToFile(listId, reminderList);

    return reminder;
  }

  public Reminder updateReminder(String listId, String reminderId, String updatedTask) throws IOException, HTTPError {
    var reminderList = getListById(listId);
    var reminder =  getReminderById(reminderList, reminderId);

    reminder.task = updatedTask;
    reminderList.items.put(reminderId, reminder);

    writeToFile(listId, reminderList);

    return reminder;
  }

  public void deleteReminder(String listId, String reminderId) throws IOException, HTTPError {
    var reminderList = getListById(listId);

    var removedReminder = reminderList.items.remove(reminderId);

    if (removedReminder == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    writeToFile(listId, reminderList);
  }

  private Path generatePath(String id) {
    var filePathStr = remindersDir + id + ".json";
    return FileSystems.getDefault().getPath(filePathStr);
  }

  private ReminderList getListById(String id) throws IOException, HTTPError {
    var filePath = generatePath(id);

    var jsonStr = fileIO.read(filePath);

    var reminderList = jsonIO.fromJson(jsonStr, ReminderList.class);

    if (reminderList == null) {
      throw new HTTPError(404, "Reminder list was not found");
    }

    return reminderList;
  }

  private Reminder getReminderById(ReminderList reminderList, String reminderId) throws HTTPError {
    var reminder = reminderList.items.get(reminderId);

    if (reminder == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    return reminder;
  }

  private void writeToFile(String listId, ReminderList reminderList) throws IOException {
    var filePath = this.generatePath(listId);
    var fileStr = jsonIO.toJson(reminderList);
    fileIO.write(filePath, fileStr);
  }
}
