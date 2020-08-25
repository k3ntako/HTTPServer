package com.k3ntako.HTTPServer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

    if (fileURL == null) {
      return null;
    }

    var fileURI = fileURL.toURI();
    var path = Paths.get(fileURI);
    return read(path);
  }

  public void patchNewLine(Path path, String str) throws IOException {
    var file = new File(path.toString());

    if (!file.exists()) {
      throw new IOException("File does not exist");
    }

    str = System.lineSeparator() + str;
    Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
  }

  public void overwrite(Path path, String str) throws IOException {
    var file = new File(path.toString());

    if (!file.exists()) {
      throw new IOException("File does not exist");
    }

    Files.write(path, str.getBytes());
  }

  public void delete(Path path) throws IOException {
    if (!Files.exists(path)) {
      throw new IOException("File does not exist");
    }

    Files.delete(path);
  }
}
