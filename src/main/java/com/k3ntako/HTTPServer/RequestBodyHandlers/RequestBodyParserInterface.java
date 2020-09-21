package com.k3ntako.HTTPServer.RequestBodyHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public interface RequestBodyParserInterface {
  Object parseBody(
      BufferedReader bufferedReader,
      Socket clientSocket,
      String contentTypeCategory,
      int contentLength
  ) throws IOException;
}
