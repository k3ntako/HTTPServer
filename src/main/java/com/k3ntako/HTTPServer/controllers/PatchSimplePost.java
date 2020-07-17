package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PatchSimplePost implements ControllerInterface {
  private FileIOInterface fileIO;

  public PatchSimplePost(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public Response getResponse(RequestInterface request) throws IOException, HTTPError {

    var body = request.getBody();
    Path path = FileSystems.getDefault().getPath("./data/simple_post.txt");

    fileIO.append(path, body);

    return new Response();
  }
}
