package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ReminderIO implements ReminderIOInterface {
  private JsonIOInterface jsonIO;
  private FileIOInterface fileIO;
  private Path filePath;
  private String remindersDir;
  private UUIDInterface uuid;

  public ReminderIO(FileIOInterface fileIO, JsonIOInterface jsonIO, UUIDInterface uuid, String dataDir) {
    this.fileIO = fileIO;
    this.jsonIO = jsonIO;
    this.uuid = uuid;

    this.filePath = FileSystems.getDefault().getPath(dataDir);
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

  public Reminder getReminderByIds(String listId, String reminderId) throws IOException {
    var reminderList = this.getListById(listId);

    if(reminderList == null){
      return null;
    }

    return reminderList.items.get(reminderId);
  }

  public Reminder addReminder(String listId, String task) throws IOException {
    var reminderList = getListById(listId);

    // check if reminderList exists

    var reminder = new Reminder(uuid.generate(), task);
    reminderList.items.put(reminder.id, reminder);

    var filePath = this.generatePath(listId);
    var fileStr = jsonIO.toJson(reminderList);
    fileIO.write(filePath, fileStr);

    return reminder;
  }

  public Reminder updateReminder(String listId, String reminderId, String updatedTask) throws IOException {
    var reminderList = getListById(listId);
    // check if reminderList exists

    var reminder = reminderList.items.get(reminderId);

    // check if reminder exists

    reminder.task = updatedTask;
    reminderList.items.put(reminderId, reminder);

    // write private method to write to file
    var filePath = this.generatePath(listId);
    var fileStr = jsonIO.toJson(reminderList);
    fileIO.write(filePath, fileStr);

    return reminder;
  }

  public void deleteReminder(String listId, String reminderId) throws IOException {
    var reminderList = getListById(listId);
    // check if reminderList exists

    var removedReminder = reminderList.items.remove(reminderId);

    if(removedReminder == null){
      // check if reminder exists
    }

    // write private method to write to file
    var filePath = this.generatePath(listId);
    var fileStr = jsonIO.toJson(reminderList);
    fileIO.write(filePath, fileStr);
  }

  private Path generatePath(String id){
    var filePathStr = remindersDir + id + ".json";
    return FileSystems.getDefault().getPath(filePathStr);
  }

  private ReminderList getListById(String id) throws IOException {
    var filePath = generatePath(id);

    var jsonStr = fileIO.read(filePath);

    return jsonIO.fromJson(jsonStr, ReminderList.class);
  }
}
