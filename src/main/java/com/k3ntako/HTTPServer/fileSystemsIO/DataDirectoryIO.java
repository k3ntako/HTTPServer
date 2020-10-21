package com.k3ntako.HTTPServer.fileSystemsIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DataDirectoryIO {
  private FileIOInterface fileIO;
  private String dataDirectory;

  public DataDirectoryIO(FileIOInterface fileIO, String dataDirectory) {
    this.fileIO = fileIO;
    this.dataDirectory = dataDirectory;
  }

  public void write(String strPath, String str) throws IOException {
    var path = generatePath(strPath);
    fileIO.write(path, str);
  }

  public void write(String strPath, byte[] bytes) throws IOException {
    var path = generatePath(strPath);
    fileIO.write(path, bytes);
  }

  public String readString(String strPath) throws IOException {
    var path = generatePath(strPath);
    return fileIO.readString(path);
  }

  public byte[] readAllBytes(String strPath) throws IOException {
    var path = generatePath(strPath);
    return fileIO.readAllBytes(path);
  }

  public byte[] readAllBytesById(String directory, String id) throws IOException {
    var path = generatePath(directory);
    var files = fileIO.listFiles(path);

    if (files == null) {
      return null;
    }

    for (File file : files) {
      var name = file.getName();

      var nameParts = name.split("\\.");

      if (nameParts.length == 2 && nameParts[0].equals(id)) {
        return fileIO.readAllBytes(file.toPath());
      }
    }

    return null;
  }

  public void delete(String strPath) throws IOException {
    var path = generatePath(strPath);
    fileIO.delete(path);
  }

  public void deleteById(String directory, String id) throws IOException {
    var path = generatePath(directory);
    var files = fileIO.listFiles(path);

    if (files == null) {
      throw new IOException("File not found");
    }

    for (File file : files) {
      var name = file.getName();

      var nameParts = name.split("\\.");

      if (nameParts.length == 2 && nameParts[0].equals(id)) {
        fileIO.delete(file.toPath());
        break;
      }
    }
  }

  private Path generatePath(String strPath) {
    var dataStrPath = dataDirectory + "/" + strPath;
    return FileSystems.getDefault().getPath(dataStrPath);
  }
}
