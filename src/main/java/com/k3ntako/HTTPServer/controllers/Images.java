package com.k3ntako.HTTPServer.controllers;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.*;
import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.utilities.FileExtensions;
import com.k3ntako.HTTPServer.utilities.UUIDInterface;

import java.io.IOException;

public class Images {
  final private DataDirectoryIO dataDirectoryIO;
  final private UUIDInterface uuid;
  private FileExtensions fileExtensions;

  public Images(DataDirectoryIO dataDirectoryIO, UUIDInterface uuid, FileExtensions fileExtensions) {
    this.dataDirectoryIO = dataDirectoryIO;
    this.uuid = uuid;
    this.fileExtensions = fileExtensions;
  }

  public ResponseInterface get(RequestInterface request, ResponseInterface response) throws HTTPError, IOException {
    var imageId = request.getRouteParam("image_id");

    var image = dataDirectoryIO.readAllBytesById("images", imageId);

    if (image == null) {
      throw new HTTPError(404, "Image was not found.");
    }

    response.setBody(image);
    return response;
  }

  public ResponseInterface post(RequestInterface request, ResponseInterface response) throws IOException, HTTPError {
    var fileBytes = (byte[]) request.getBody();

    var contentType = request.getHeaders().get("Content-Type");
    var fileExt = fileExtensions.getFromMimeType(contentType);

    if (fileExt == null) {
      throw new HTTPError(400, "Invalid image type");
    }

    var uuid = this.uuid.generate();
    dataDirectoryIO.write("images/" + uuid + fileExt, fileBytes);

    var responseJson = new JsonObject();
    responseJson.addProperty("id", uuid);

    response.setBody(responseJson);

    return response;
  }

  public ResponseInterface delete(RequestInterface request, ResponseInterface response) throws HTTPError {
    var imageId = request.getRouteParam("image_id");
    try {
      dataDirectoryIO.deleteById("images", imageId);
    } catch (IOException e) {
      throw new HTTPError(404, "Image was not found.");
    }

    return response;
  }
}
