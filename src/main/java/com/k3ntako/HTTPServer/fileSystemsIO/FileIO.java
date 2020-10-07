package com.k3ntako.HTTPServer.fileSystemsIO;

import com.k3ntako.HTTPServer.HTTPError;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class FileIO implements FileIOInterface {
  @Override
  public void write(Path path, String str) throws IOException {
    write(path, str.getBytes());
  }

  @Override
  public void write(Path path, byte[] bytes) throws IOException {
    var file = new File(path.toString());
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }

    Files.write(path, bytes);
  }

  @Override
  public String readString(Path path) throws IOException {
    if (!doesFileExist(path)) {
      return null;
    }

    return Files.readString(path);
  }


  @Override
  public byte[] readAllBytes(Path path) throws IOException {
    if (!doesFileExist(path)) {
      return null;
    }

    return Files.readAllBytes(path);
  }

  public byte[] getResourceIfExists(String fileName) throws IOException {
    var inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

    if (inputStream == null) {
      return null;
    }

    var targetArray = new byte[inputStream.available()];
    inputStream.read(targetArray);

    return targetArray;
  }

  public String getResource(String fileName) throws IOException {
    var inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new IOException("Resource was null");
    }

    var scanner = new Scanner(inputStream).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }

  public String probeResourceContentType(String fileName) throws HTTPError {
    try {
      var url = this.getClass().getClassLoader().getResource(fileName);

      if (url == null) {
        throw new HTTPError(404, "Resource not found");
      }

      Path path = new File(url.toURI()).toPath();

      return Files.probeContentType(path);
    } catch (IOException e) {
      throw new HTTPError(404, e.getMessage());
    } catch (URISyntaxException e) {
      throw new HTTPError(500, e.getMessage());
    }
  }

  public void patchNewLine(Path path, String str) throws IOException {
    if (!doesFileExist(path)) {
      throw new IOException("File does not exist");
    }

    str = System.lineSeparator() + str;
    Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
  }

  public void overwrite(Path path, String str) throws IOException {
    if (!doesFileExist(path)) {
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

  private Boolean doesFileExist(Path path) {
    var file = new File(path.toString());
    return file.exists();
  }
}
