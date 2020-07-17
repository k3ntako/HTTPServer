package com.k3ntako.HTTPServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO implements FileIOInterface {
  @Override
  public void write(Path path, String str) throws IOException {
    var file = new File(path.toString());
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }

    Files.write(path, str.getBytes());
  }

  @Override
  public String read(Path path) throws IOException {
    var file = new File(path.toString());
    if (!file.exists()) {
      return null;
    }

    return Files.readString(path);
  }

  @Override
  public void append(Path path, String appendStr) throws IOException, HTTPError {
    var fileStr = this.read(path);

    if (fileStr == null) {
      throw new HTTPError(404, "File to be appended was not found");
    }

    var newStr = fileStr + appendStr;
    this.write(path, newStr);
  }

  @Override
  public void delete(Path path) throws HTTPError {
    var file = new File(path.toString());
    if (!file.exists()) {
      throw new HTTPError(404, "File was not found");
    }

    file.delete();
  }
}
