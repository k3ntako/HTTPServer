package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class Reminders {
  private TextFile textFile;

  public Reminders(TextFile textFile) {
    this.textFile = textFile;
  }

  public Response post(RequestInterface request) throws IOException, HTTPError {
    var body = request.getBody();
    validateBody(body);

    var fileName = textFile.saveFile(body);

    var response = new Response();
    response.setBody(fileName);

    return response;
  }

  private void validateBody(String body) throws HTTPError {
    if (body.contains("\n")) {
      throw new HTTPError(400, "Request body should not be multiline");
    }
  }

  public Response get(RequestInterface request) throws IOException, HTTPError {
    var id = request.getRouteParam("id");

    var response = new Response();

    var content = textFile.readFile(id);
    if (content == null) {
      throw new HTTPError(404, "Reminder was not found");
    }

    response.setBody(content);

    return response;
  }

  public Response patch(RequestInterface request) throws HTTPError {
    var body = request.getBody();
    validateBody(body);

    var id = request.getRouteParam("id");

    try {
      textFile.patchFile(id, body);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    return new Response();
  }

  public Response put(RequestInterface request) throws HTTPError {
    var body = request.getBody();
    validateBody(body);

    var id = request.getParam("id");

    try {
      textFile.overwriteFile(id, body);
    } catch (IOException e) {
      throw new HTTPError(404, "Reminder was not found");
    }

    return new Response();
  }
}
