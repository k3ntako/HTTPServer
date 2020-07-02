package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {
  public void write(Path path, String str) throws IOException {
    Files.write(path, str.getBytes());
  }

  public String read(Path path) throws IOException {
    return Files.readString(path);
  }
}
