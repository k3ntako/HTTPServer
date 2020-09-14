package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class ReminderLists {
  final private ReminderIOInterface reminderIO;

  public ReminderLists(ReminderIOInterface reminderIO) {
    this.reminderIO = reminderIO;
  }

  public ResponseInterface post(RequestInterface request) throws IOException {
    var reminderList = reminderIO.createNewList();

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setJsonBody(reminderList);

    return response;
  }
}
