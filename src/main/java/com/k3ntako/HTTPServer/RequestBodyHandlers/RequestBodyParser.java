package com.k3ntako.HTTPServer.RequestBodyHandlers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestBodyParser implements RequestBodyParserInterface {
  @Override
  public Object parseBody(
      BufferedReader bufferedReader,
      Socket clientSocket,
      String contentTypeCategory,
      int contentLength
  ) throws IOException {
    if ("image".equals(contentTypeCategory)) {
      return this.readBinaryBody(clientSocket, contentLength);
    }

    return this.readTextBody(bufferedReader, contentLength);
  }


  private String readTextBody(BufferedReader bufferedReader, int contentLength) throws IOException {
    var bodyStr = "";
    char character;

    while (bodyStr.length() < contentLength) {
      character = (char) bufferedReader.read();
      bodyStr = bodyStr.concat(String.valueOf(character));
    }
    return bodyStr;
  }

  private byte[] readBinaryBody(Socket clientSocket, int contentLength) throws IOException {
    final var bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
    final var outputStream = new ByteArrayOutputStream();

    final var defaultSize = 4096;
    final var size = Math.min(contentLength, defaultSize);
    var totalLengthRead = 0;
    byte[] data = new byte[size];

    while (totalLengthRead < contentLength) {
      final var bytesRead = bufferedInputStream.read(data, 0, data.length);
      outputStream.write(data, 0, bytesRead);

      totalLengthRead += bytesRead;

      final var remaining = contentLength - totalLengthRead;
      if (remaining < size) {
        data = new byte[remaining];
      }
    }

    return outputStream.toByteArray();
  }

}
