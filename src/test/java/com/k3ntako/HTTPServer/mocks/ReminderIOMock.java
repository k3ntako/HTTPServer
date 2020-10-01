package com.k3ntako.HTTPServer.mocks;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.fileSystemsIO.ReminderIOInterface;
import com.k3ntako.HTTPServer.utilities.ReminderJsonCreator;
import com.k3ntako.HTTPServer.utilities.UUIDInterface;

import java.io.IOException;

public class ReminderIOMock implements ReminderIOInterface {
  public Boolean createNewListCalled = false;
  public String reminderIdForGet;
  public String listIdForGet;
  public String listIdForWrite;
  public String taskForWrite;
  private UUIDInterface uuid;
  private JsonObject reminderToReturn;
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

  public ReminderIOMock(JsonObject reminderToReturn) {
    this.reminderToReturn = reminderToReturn;
    this.isReminderToReturnSet = true;
  }

  public ReminderIOMock(IOException mockException) {
    this.mockException = mockException;
  }

  @Override
  public JsonObject createNewList() throws IOException {
    throwIfExceptionExists();
    this.createNewListCalled = true;

    return ReminderJsonCreator.createReminderList("mock-new-list-id");
  }

  @Override
  public JsonObject getReminderByIds(String listId, String reminderId) throws IOException {
    throwIfExceptionExists();

    listIdForGet = listId;
    reminderIdForGet = reminderId;

    if (isReminderToReturnSet) {
      return reminderToReturn;
    }

    return ReminderJsonCreator.createReminder(reminderId, "ReminderIOMock.getReminderByIds: mock task text");
  }

  @Override
  public JsonObject addReminder(String listId, String task) throws IOException {
    throwIfExceptionExists();

    listIdForWrite = listId;
    taskForWrite = task;
    return ReminderJsonCreator.createReminder(uuid.generate(), task);
  }

  @Override
  public JsonObject updateReminder(String listId, String reminderId, String updatedTask) throws IOException {
    throwIfExceptionExists();
    listIdForUpdate = listId;
    reminderIdForUpdate = reminderId;
    taskForUpdate = updatedTask;


    return ReminderJsonCreator.createReminder(reminderId, updatedTask);
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
