package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class SimplePost implements ControllerInterface {
  private FileIOInterface fileIO;
  private UUIDInterface uuid;

  public SimplePost(FileIOInterface fileIO, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.uuid = uuid;
  }

  public Response getResponse(RequestInterface request) throws IOException, HTTPError {
    var body = request.getBody();
    validateBody(body);

    var filePath = "./data/" + uuid.generate() + ".txt";
    Path path = FileSystems.getDefault().getPath(filePath);

    fileIO.write(path, body);

    return new Response();
  }

  private void validateBody(String body) throws HTTPError {
    if(body.contains("\n")){
      throw new HTTPError(400, "Request body should not be multiline");
    }
  }
}
