package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class Reminders {
  private ReminderIOInterface reminderIO;

  public Reminders(ReminderIOInterface reminderIO) {
    this.reminderIO = reminderIO;
  }

  public ResponseInterface post(RequestInterface request) throws IOException, HTTPError {
    var body = request.getBody();
    validateBody(body);

    var listId = request.getRouteParam("list_id");
    var reminder = reminderIO.addReminder(listId, body);

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setJsonBody(reminder);

    return response;
  }

  private void validateBody(String body) throws HTTPError {
    if (body.contains("\n")) {
      throw new HTTPError(400, "Request body should not be multiline");
    }
  }

  public ResponseInterface get(RequestInterface request) throws IOException, HTTPError {
    var listId = request.getRouteParam("list_id");
    var reminderId = request.getRouteParam("reminder_id");

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    var content = reminderIO.getReminderByIds(listId, reminderId);
    if (content == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    response.setJsonBody(content);

    return response;
  }

  public ResponseInterface put(RequestInterface request) throws HTTPError {
    var body = request.getBody();
    var listId = request.getRouteParam("list_id");
    var reminderId = request.getRouteParam("reminder_id");

    try {
      reminderIO.updateReminder(listId, reminderId, body);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    var jsonIO = new JsonIO(new Gson());
    return new Response(jsonIO);
  }

  public ResponseInterface delete(RequestInterface request) throws HTTPError {
    var listId = request.getRouteParam("list_id");
    var reminderId = request.getRouteParam("reminder_id");

    try {
      reminderIO.deleteReminder(listId, reminderId);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    var jsonIO = new JsonIO(new Gson());
    return new Response(jsonIO);
  }
}
