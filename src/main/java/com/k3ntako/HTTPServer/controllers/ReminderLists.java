package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class ReminderLists {
  final private ReminderIOInterface reminderIO;

  public ReminderLists(ReminderIOInterface reminderIO) {
    this.reminderIO = reminderIO;
  }

  public ResponseInterface post(RequestInterface request, ResponseInterface response) throws IOException, HTTPError {
    var reminderList = reminderIO.createNewList();

    response.setBody(reminderList);

    return response;
  }
}
