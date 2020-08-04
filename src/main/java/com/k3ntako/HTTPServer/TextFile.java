package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class TextFile {
  private FileIOInterface fileIO;
  private UUIDInterface uuid;

  public TextFile(FileIOInterface fileIO, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.uuid = uuid;
  }

  public String saveFile(String text) throws IOException {
    var fileUUID = uuid.generate();
    var filePath = "./data/" + fileUUID + ".txt";
    Path path = FileSystems.getDefault().getPath(filePath);

    fileIO.write(path, text);

    return fileUUID;
  }

  public String readFile(String fileUUID) throws IOException {
    var filePath = "./data/" + fileUUID + ".txt";
    Path path = FileSystems.getDefault().getPath(filePath);

    return fileIO.read(path);
  }
}
