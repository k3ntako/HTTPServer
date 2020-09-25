package com.k3ntako.HTTPServer;

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

  public String read(String strPath) throws IOException {
    var path = generatePath(strPath);
    return fileIO.read(path);
  }

  private Path generatePath(String strPath) {
    var dataStrPath = dataDirectory + "/" + strPath;
    return FileSystems.getDefault().getPath(dataStrPath);
  }
}
