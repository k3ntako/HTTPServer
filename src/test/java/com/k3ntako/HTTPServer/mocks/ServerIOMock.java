package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ServerIOInterface;

import java.io.*;
import java.net.Socket;

public class ServerIOMock implements ServerIOInterface {
  private String clientInput;
  private BufferedReader bufferedReader;
  private PrintWriterWrapperMock printWriter;
  
  public ServerIOMock(String clientInput) {
    this.clientInput = clientInput;
  }

  public void init(Socket clientSocket) {
    bufferedReader = new BufferedReader(new StringReader(this.clientInput));
    printWriter = new PrintWriterWrapperMock();
  }

  public String readLine() throws IOException {
    return bufferedReader.readLine();
  }

  public char read() throws IOException {
    return (char) bufferedReader.read();
  }

  public void sendData(String data) {
    this.printWriter.sendData(data);
  }

  public void close() throws IOException {
    this.bufferedReader.close();
    this.printWriter.close();
  }

  public String getSentData() {
    return printWriter.getSentData();
  }

}
