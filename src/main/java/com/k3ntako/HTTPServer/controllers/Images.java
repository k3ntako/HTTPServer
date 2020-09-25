package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.*;

import java.io.IOException;
import java.util.HashMap;

public class Images {
  final private DataDirectoryIO dataDirectoryIO;
  final private UUIDInterface uuid;

  public Images(DataDirectoryIO dataDirectoryIO, UUIDInterface uuid) {
    this.dataDirectoryIO = dataDirectoryIO;
    this.uuid = uuid;
  }

  public ResponseInterface post(RequestInterface request) throws IOException {
    var fileBytes = (byte[]) request.getBody();

    var uuid = this.uuid.generate();
    dataDirectoryIO.write("images/" + uuid + ".png", fileBytes);

    var responseHashMap = new HashMap<String, String>();
    responseHashMap.put("id", uuid);

    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setJsonBody(responseHashMap);

    return response;
  }
}
