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
  public void append(Path path, String appendStr) throws IOException {
    var fileStr = this.read(path);
    var newStr = fileStr + appendStr;
    this.write(path, newStr);
  }
}
