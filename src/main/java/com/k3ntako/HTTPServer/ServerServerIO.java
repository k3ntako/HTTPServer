package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapper;
import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapperInterface;

import java.io.*;
import java.net.Socket;

public class ServerServerIO implements ServerIOInterface {
  private BufferedReader bufferedReader;
  private PrintWriterWrapperInterface printWriter;

  public void init(Socket clientSocket) throws IOException {
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

  public void sendData(String data) {
    printWriter.sendData(data);
  }

  public void close() throws IOException {
    bufferedReader.close();
    printWriter.close();
  }

}
