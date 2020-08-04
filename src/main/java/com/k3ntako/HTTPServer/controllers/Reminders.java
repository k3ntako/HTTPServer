package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Reminders implements ControllerInterface {
  private FileIOInterface fileIO;
  private UUIDInterface uuid;

  public Reminders(FileIOInterface fileIO, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.uuid = uuid;
  }

  public Response getResponse(RequestInterface request) throws IOException, HTTPError {
    var body = request.getBody();
    validateBody(body);

    var fileUUID = uuid.generate();
    var filePath = "./data/" + fileUUID + ".txt";
    Path path = FileSystems.getDefault().getPath(filePath);

    fileIO.write(path, body);

    var response = new Response();
    response.setBody(fileUUID);

    return response;
  }

  private void validateBody(String body) throws HTTPError {
    if(body.contains("\n")){
      throw new HTTPError(400, "Request body should not be multiline");
    }
  }
}
