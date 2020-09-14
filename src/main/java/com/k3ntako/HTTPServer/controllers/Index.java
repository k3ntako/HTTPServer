package com.k3ntako.HTTPServer.controllers;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.*;

public class Index {
  private FileIOInterface fileIO;

  public Index(FileIOInterface fileIO) {
    this.fileIO = fileIO;
  }

  public ResponseInterface get(RequestInterface request) {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);

    try {
      var file = fileIO.getResource("./pages/index.html");
      response.addHeader("Content-Type", "text/html; charset=UTF-8");
      response.setBody(file);
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return response;
  }
}
