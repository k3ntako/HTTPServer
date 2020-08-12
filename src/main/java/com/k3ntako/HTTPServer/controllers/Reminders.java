package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;
import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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

  public Response get(RequestInterface request) throws IOException {
    var params = request.getParams();

    var id = params.get("id");

    var response = new Response();

    var content = textFile.readFile(id);
    response.setBody(content);

    return response;
  }
}
