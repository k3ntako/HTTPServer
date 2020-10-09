package com.k3ntako.HTTPServer.fileSystemsIO;

import com.k3ntako.HTTPServer.HTTPError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileIOInterface {
  void write(Path path, String str) throws IOException;

  void write(Path path, byte[] bytes) throws IOException;

  String readString(Path path) throws IOException;

  byte[] readAllBytes(Path path) throws IOException;

  void patchNewLine(Path path, String str) throws IOException;

  void overwrite(Path path, String str) throws IOException;

  void delete(Path path) throws IOException;

  String getResource(String fileName) throws IOException;

  byte[] getResourceIfExists(String fileName) throws IOException;

  File[] listFiles(Path path);
}
