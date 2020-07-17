package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DeleteSimplePost implements ControllerInterface {
  private FileIOInterface fileIO;

  public DeleteSimplePost(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public Response getResponse(RequestInterface request) throws IOException {
    Path path = FileSystems.getDefault().getPath("./data/simple_post.txt");
    fileIO.delete(path);

    return new Response();
  }
}
