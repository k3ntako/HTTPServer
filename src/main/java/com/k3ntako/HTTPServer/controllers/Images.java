package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;

public class Images {
  final private FileIOInterface fileIO;
  final private UUIDInterface uuid;

  public Images(FileIOInterface fileIO, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.uuid = uuid;
  }

  public ResponseInterface post(RequestInterface request) throws IOException {
    var fileBytes = (byte[]) request.getBody();

    var uuid = this.uuid.generate();
    var path = FileSystems.getDefault().getPath("./data/images/" + uuid + ".png");
    fileIO.write(path, fileBytes);

    var responseHashMap = new HashMap<String, String>();
    responseHashMap.put("id", uuid);

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setJsonBody(responseHashMap);

    return response;
  }
}
