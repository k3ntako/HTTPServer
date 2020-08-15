package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class TextFile {
  private final FileIOInterface fileIO;
  private final UUIDInterface uuid;

  public TextFile(FileIOInterface fileIO, UUIDInterface uuid) {
    this.fileIO = fileIO;
    this.uuid = uuid;
  }

  public String saveFile(String text) throws IOException {
    var fileUUID = uuid.generate();
    var path = generatePathForUUID(fileUUID);

    fileIO.write(path, text);

    return fileUUID;
  }

  public String readFile(String fileUUID) throws IOException {
    var path = generatePathForUUID(fileUUID);

    return fileIO.read(path);
  }

  public void patchFile(String fileUUID, String text) throws IOException {
    var path = generatePathForUUID(fileUUID);

    fileIO.patchNewLine(path, text);
  }

  private Path generatePathForUUID(String fileUUID) {
    var strPath = "./data/" + fileUUID + ".txt";
    return FileSystems.getDefault().getPath(strPath);
  }
}
