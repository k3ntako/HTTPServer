package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapper;

import java.io.*;
import java.net.Socket;

public class ClientSocketIO implements ClientSocketIOInterface {
  private Socket clientSocket;
  private BufferedReader bufferedReader;
  private PrintWriterWrapper printWriter;

  public void init(Socket clientSocket) throws IOException {
    this.clientSocket = clientSocket;

    var inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
    bufferedReader = new BufferedReader(inputStreamReader);

    var outputStream = clientSocket.getOutputStream();
    printWriter = new PrintWriterWrapper(outputStream, true);
  }

  public String readLine() throws IOException {
    return bufferedReader.readLine();
  }

  public char read() throws IOException {
    return (char) bufferedReader.read();
  }

  public String readTextBody(int contentLength) throws IOException {
    var bodyStr = "";
    char character;

    while (bodyStr.length() < contentLength) {
      character = (char) bufferedReader.read();
      bodyStr = bodyStr.concat(String.valueOf(character));
    }
    return bodyStr;
  }

  public byte[] readBinaryBody(int contentLength) throws IOException {
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


  public void sendData(String data) {
    printWriter.sendData(data);
  }

  public void close() throws IOException {
    bufferedReader.close();
    printWriter.close();
  }

}
