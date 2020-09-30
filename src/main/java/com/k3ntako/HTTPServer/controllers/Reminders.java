package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class Reminders {
  final private ReminderIOInterface reminderIO;

  public Reminders(ReminderIOInterface reminderIO) {
    this.reminderIO = reminderIO;
  }

  public ResponseInterface post(RequestInterface request, ResponseInterface response) throws IOException, HTTPError {
    var body = (String) request.getBody();
    validateBody(body);

    var listId = request.getRouteParam("list_id");
    var reminder = reminderIO.addReminder(listId, body);

    response.setBody(reminder);

    return response;
  }

  private void validateBody(String body) throws HTTPError {
    if (body.contains("\n")) {
      throw new HTTPError(400, "Request body should not be multiline");
    }
  }

  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws IOException, HTTPError {
    var listId = request.getRouteParam("list_id");
    var reminderId = request.getRouteParam("reminder_id");

    var content = reminderIO.getReminderByIds(listId, reminderId);
    if (content == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    response.setBody(content);

    return response;
  }

  public ResponseInterface put(RequestInterface request, ResponseInterface response) throws HTTPError {
    var body = (String) request.getBody();
    var listId = request.getRouteParam("list_id");
    var reminderId = request.getRouteParam("reminder_id");

    try {
      reminderIO.updateReminder(listId, reminderId, body);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    return response;
  }

  public ResponseInterface delete(RequestInterface request, ResponseInterface response) throws HTTPError {
    var listId = request.getRouteParam("list_id");
    var reminderId = request.getRouteParam("reminder_id");

    try {
      reminderIO.deleteReminder(listId, reminderId);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    return response;
  }
}
