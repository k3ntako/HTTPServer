package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;
import com.k3ntako.HTTPServer.fileSystemsIO.FileIOInterface;

import java.io.IOException;

public class PublicFiles {
  final private FileIOInterface fileIO;

  public PublicFiles(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws HTTPError, IOException {
    var route = request.getRoute();
    var fileName = "public" + route;

    // Looks for index.html in case the route is a directory.
    // Ideally, it would only perform this if the route is a directory,
    // however, Unix directories are files, so there is no simple check.
    byte[] fileBytes = findIndexHTML(fileName);
    String contentType = null;

    if (fileBytes != null) {
      contentType = "text/html";
    } else {
      // If directory, returns file names inside.
      fileBytes = fileIO.getResourceIfExists(fileName);

      if (fileBytes != null) {
        contentType = fileIO.probeResourceContentType(fileName);
      }
    }

    if (fileBytes == null) {
      throw new HTTPError(404, "Not found");
    }

    response.addHeader("Content-Type", contentType);
    response.setBody(fileBytes);

    return response;
  }

  private byte[] findIndexHTML(String pathStr) throws IOException {
    var lastChar = pathStr.substring(pathStr.length() - 1);
    var hasSlash = lastChar.equals("/");

    if (!hasSlash) {
      pathStr += "/";
    }

    return fileIO.getResourceIfExists(pathStr + "index.html");
  }

}
