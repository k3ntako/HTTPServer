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

    var fileName = reminderIO.addNew(body);

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setBody(fileName);

    return response;
  }

  private void validateBody(String body) throws HTTPError {
    if (body.contains("\n")) {
      throw new HTTPError(400, "Request body should not be multiline");
    }
  }

  public ResponseInterface get(RequestInterface request) throws IOException, HTTPError {
    var id = request.getRouteParam("id");

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    var content = reminderIO.getById(id);
    if (content == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    response.setJsonBody(content);

    return response;
  }

  public ResponseInterface put(RequestInterface request) throws HTTPError {
    var body = request.getBody();
    var id = request.getRouteParam("id");

    try {
      reminderIO.overwrite(id, body);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    var jsonIO = new JsonIO(new Gson());
    return new Response(jsonIO);
  }

  public ResponseInterface delete(RequestInterface request) throws HTTPError {
    var id = request.getRouteParam("id");

    try {
      reminderIO.delete(id);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    var jsonIO = new JsonIO(new Gson());
    return new Response(jsonIO);
  }
}
