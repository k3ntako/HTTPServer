package com.k3ntako.HTTPServer;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;

public class ReminderIO {
  private Gson gson;
  private FileIOInterface fileIO;
  private Path filePath;
  private UUIDInterface uuid;

  public ReminderIO(FileIOInterface fileIO, Gson gson, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.gson = gson;
    this.uuid = uuid;

    var strPath = "./data/reminders.json";
    this.filePath = FileSystems.getDefault().getPath(strPath);
  }

  Reminder getById(String id) throws IOException {
    var reminderFile = all();
    return reminderFile.reminders.get(id);
  }

  String write(String task) throws IOException {
    var reminder = new Reminder(task, uuid.generate());
    var reminderFile = all();

    reminderFile.reminders.put(reminder.id, reminder);

    var fileStr = gson.toJson(reminderFile);
    fileIO.write(filePath, fileStr);

    return reminder.id;
  }

  private ReminderFile all() throws IOException {
    var jsonStr = fileIO.read(filePath);
    var reminderFile = gson.fromJson(jsonStr, ReminderFile.class);

    if (reminderFile == null) {
      reminderFile = new ReminderFile(uuid.generate());
    }

    return reminderFile;
  }

}


//
//  void jsonToFile(String fileName, ReminderFile reminderFile){
//
//  }
//
//  private Path generatePathForUUID(String fileUUID) {
//    var strPath = "./data/" + fileUUID + ".json";
//    return FileSystems.getDefault().getPath(strPath);
//  }