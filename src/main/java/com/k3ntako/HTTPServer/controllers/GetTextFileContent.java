package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.FileIOInterface;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.Response;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class GetTextFileContent {
  private FileIOInterface fileIO;

  public GetTextFileContent(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public Response get(RequestInterface request) throws IOException {
    Path path = FileSystems.getDefault().getPath("./data/reminders.txt");
    var content = fileIO.read(path);

    var response = new Response();
    response.setBody(content);

    return response;
  }
}
