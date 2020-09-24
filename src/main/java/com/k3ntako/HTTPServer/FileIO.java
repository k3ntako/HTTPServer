package com.k3ntako.HTTPServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

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

  public String getResourceIfExists(String fileName) {
    var inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

    if (inputStream == null) {
      return null;
    }

    var scanner = new Scanner(inputStream).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }

  public String getResource(String fileName) throws IOException {
    var inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new IOException("Resource was null");
    }

    var scanner = new Scanner(inputStream).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }

  @Override
  public Boolean isResourceDirectory(String fileName) {
    var resourceURL = this.getClass().getClassLoader().getResource(fileName);

    if (resourceURL == null) {
      return null;
    }

    // file.isDirectory() does not work for resource directories/files in a Jar file
    var resourceUrlStr = resourceURL.toString();
    var lastChar = resourceUrlStr.substring(resourceUrlStr.length() - 1);
    return lastChar.equals("/");
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
