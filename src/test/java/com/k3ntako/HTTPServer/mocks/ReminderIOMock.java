package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.Reminder;
import com.k3ntako.HTTPServer.ReminderIOInterface;
import com.k3ntako.HTTPServer.UUIDInterface;

import java.io.IOException;

public class ReminderIOMock implements ReminderIOInterface {
  public String idForGet;
  public String taskForWrite;
  private UUIDInterface uuid;
  private Reminder reminderToReturn;
  private boolean isReminderToReturnSet = false;
  public String overwriteId;
  public String overwriteTask;
  public String deleteId;
  private IOException mockException;

  public ReminderIOMock() {
    this.uuid = new UUIDMock();
  }

  public ReminderIOMock(Reminder reminderToReturn) {
    this.reminderToReturn = reminderToReturn;
    this.isReminderToReturnSet = true;
  }

  public ReminderIOMock(IOException mockException) {
    this.mockException = mockException;
  }

  @Override
  public Reminder getById(String id) throws IOException {
    throwIfExceptionExists();
    idForGet = id;

    if (isReminderToReturnSet) {
      return reminderToReturn;
    }

    return new Reminder(id, "ReminderIOMock: mock task text");
  }

  @Override
  public String addNew(String task) throws IOException {
    throwIfExceptionExists();
    taskForWrite = task;
    return uuid.generate();
  }

  @Override
  public void overwrite(String id, String task) throws IOException {
    throwIfExceptionExists();
    overwriteId = id;
    overwriteTask = task;
  }

  @Override
  public void delete(String id) throws IOException {
    throwIfExceptionExists();
    deleteId = id;
  }

  private void throwIfExceptionExists() throws IOException {
    if(mockException != null){
      throw mockException;
    }
  }
}
