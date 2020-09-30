package com.k3ntako.HTTPServer;

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

  String getResourceIfExists(String fileName);

  Boolean isResourceDirectory(String fileName);
}
