package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class SimplePost implements ControllerInterface {
  private FileIOInterface fileIO;

  public SimplePost(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public Response getResponse(RequestInterface request) throws IOException {
    var body = request.getBody();
    Path path = FileSystems.getDefault().getPath("./data/simple_post.txt");

    fileIO.write(path, body);

    return new Response();
  }
}
