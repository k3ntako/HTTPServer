package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class PublicFiles {
  final private FileIOInterface fileIO;

  public PublicFiles(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws HTTPError {
    try {
      final var route = request.getRoute();
      var isDirectory = fileIO.isResourceDirectory("public" + route);

      String file = null;
      if (isDirectory == null || isDirectory) {
        file = fileIO.getResource("public" + route + "index.html");
      }

      if (file == null) {
        file = fileIO.getResource("public" + route);
      }

      if (file == null) {
        throw new HTTPError(404, "File was not found");
      }

      response.addHeader("Content-Type", "text/html; charset=UTF-8");
      response.setBody(file);
    } catch (IOException exception) {
      throw new HTTPError(404, "File was not found");
    }

    return response;
  }
}
