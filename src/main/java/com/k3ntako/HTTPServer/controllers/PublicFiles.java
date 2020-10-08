package com.k3ntako.HTTPServer.controllers;

import com.k3ntako.HTTPServer.*;
import com.k3ntako.HTTPServer.fileSystemsIO.FileIOInterface;
import com.k3ntako.HTTPServer.utilities.MimeTypes;

import java.io.IOException;

public class PublicFiles {
  final private FileIOInterface fileIO;
  final private MimeTypes mimeTypes;

  public PublicFiles(FileIOInterface fileIO, MimeTypes mimeTypes) {
    this.fileIO = fileIO;
    this.mimeTypes = mimeTypes;
  }

  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws HTTPError, IOException {
    var route = request.getRoute();
    var fileName = "public" + route;

    // Looks for index.html in case the route is a directory.
    // Ideally, it would only perform this if the route is a directory,
    // however, Unix directories are files, so there is no simple check.
    byte[] fileBytes = findIndexHTML(fileName);
    String contentType;

    if (fileBytes != null) {
      contentType = "text/html";
    } else {
      // If directory, returns file names inside.
      fileBytes = fileIO.getResourceIfExists(fileName);
      contentType = mimeTypes.guessContentType(fileBytes, fileName);
    }

    if (fileBytes == null) {
      throw new HTTPError(404, "Not found");
    }

    if (contentType != null) {
      response.addHeader("Content-Type", contentType);
    }
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
