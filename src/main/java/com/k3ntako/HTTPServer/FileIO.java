package com.k3ntako.HTTPServer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

  public String getResource(String fileName) throws IOException, URISyntaxException {
    var fileURL = this.getClass().getClassLoader().getResource(fileName);

    if(fileURL == null) {
      return null;
    }

    var fileURI = fileURL.toURI();
    var path = Paths.get(fileURI);
    return read(path);
  }
}
