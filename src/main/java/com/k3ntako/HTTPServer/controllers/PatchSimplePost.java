package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.ControllerInterface;
import com.k3ntako.HTTPServer.FileIOInterface;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PatchSimplePost implements ControllerInterface {
  private FileIOInterface fileIO;

  public PatchSimplePost(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public Response getResponse(RequestInterface request) throws IOException {

    var body = request.getBody();
    Path path = FileSystems.getDefault().getPath("./data/simple_post.txt");

    fileIO.append(path, body);

    return new Response();
  }
}
