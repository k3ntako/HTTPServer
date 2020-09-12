package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.Reminder;
import com.k3ntako.HTTPServer.ReminderIOInterface;
import com.k3ntako.HTTPServer.ReminderList;
import com.k3ntako.HTTPServer.UUIDInterface;

import java.io.IOException;

public class ReminderIOMock implements ReminderIOInterface {
  public Boolean createNewListCalled = false;
  public String reminderIdForGet;
  public String listIdForGet;
  public String listIdForWrite;
  public String taskForWrite;
  private UUIDInterface uuid;
  private Reminder reminderToReturn;
  private boolean isReminderToReturnSet = false;
  public String listIdForUpdate;
  public String reminderIdForUpdate;
  public String taskForUpdate;
  public String reminderIdForDelete;
  public String listIdForDelete;
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
  public ReminderList createNewList() throws IOException {
    throwIfExceptionExists();
    this.createNewListCalled = true;
    return new ReminderList("mock-new-list-id");
  }

  @Override
  public Reminder getReminderByIds(String listId, String reminderId) throws IOException {
    throwIfExceptionExists();

    listIdForGet = listId;
    reminderIdForGet = reminderId;

    if (isReminderToReturnSet) {
      return reminderToReturn;
    }

    return new Reminder(reminderId, "ReminderIOMock: mock task text");
  }

  @Override
  public Reminder addReminder(String listId, String task) throws IOException {
    throwIfExceptionExists();

    listIdForWrite = listId;
    taskForWrite = task;
    return new Reminder(uuid.generate(), "ReminderIOMock: mock task text");
  }

  @Override
  public Reminder updateReminder(String listId, String reminderId, String updatedTask) throws IOException {
    throwIfExceptionExists();
    listIdForUpdate = listId;
    reminderIdForUpdate = reminderId;
    taskForUpdate = updatedTask;

    return new Reminder(reminderId, updatedTask);
  }

  @Override
  public void deleteReminder(String listId, String reminderId) throws IOException {
    throwIfExceptionExists();
    listIdForDelete = listId;
    reminderIdForDelete = reminderId;
  }

  private void throwIfExceptionExists() throws IOException {
    if (mockException != null) {
      throw mockException;
    }
  }
}
