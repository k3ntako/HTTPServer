package com.k3ntako.HTTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public interface RequestBodyParserInterface {
  byte[] parseBody(
      Socket clientSocket,
      int contentLength
  ) throws IOException;
}
