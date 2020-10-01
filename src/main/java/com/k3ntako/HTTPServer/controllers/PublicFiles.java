package com.k3ntako.HTTPServer.controllers;
import com.k3ntako.HTTPServer.*;
import com.k3ntako.HTTPServer.fileSystemsIO.FileIOInterface;

public class PublicFiles {
  final private FileIOInterface fileIO;

  public PublicFiles(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws HTTPError {
    final var route = request.getRoute();
    var isDirectory = fileIO.isResourceDirectory("public" + route);

    String fileStr = null;
    if (isDirectory != null && isDirectory) {
      fileStr = fileIO.getResourceIfExists("public" + route + "index.html");
    }

    if (fileStr == null) {
      // if directory, returns file names inside
      fileStr = fileIO.getResourceIfExists("public" + route);
    }

    var existsButEmpty = isDirectory != null && fileStr != null && fileStr.length() == 0;
    if (existsButEmpty && isDirectory) {
      fileStr = "Directory is empty";
    } else if (existsButEmpty) {
      fileStr = "File is empty";
    }

    if (fileStr == null) {
      throw new HTTPError(404, "File was not found");
    }

    response.addHeader("Content-Type", "text/html; charset=UTF-8");
    response.setBody(fileStr);

    return response;
  }
}
