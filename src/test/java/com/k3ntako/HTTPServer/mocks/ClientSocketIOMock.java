package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ClientSocketIOInterface;

import java.io.*;
import java.net.Socket;

public class ClientSocketIOMock implements ClientSocketIOInterface {
  final private Object mockBody;
  final private BufferedReader headerBufferedReader;
  private PrintWriterWrapperMock printWriter;

  public ClientSocketIOMock(String mockHeader) {
    this.headerBufferedReader = new BufferedReader(new StringReader(mockHeader));
    this.mockBody = "";
  }

  public ClientSocketIOMock(String mockHeader, String mockBody) {
    this.headerBufferedReader = new BufferedReader(new StringReader(mockHeader));
    this.mockBody = mockBody;
  }

  public ClientSocketIOMock(String mockHeader, byte[] mockBody) {
    this.headerBufferedReader = new BufferedReader(new StringReader(mockHeader));
    this.mockBody = mockBody;
  }

  public void init(Socket socket) {
    // Socket is ignored

    printWriter = new PrintWriterWrapperMock();
  }

  public String readLine() throws IOException {
    return headerBufferedReader.readLine();
  }

  public char read() throws IOException {
    return (char) headerBufferedReader.read();
  }

  @Override
  public Object parseBody(String contentType, int contentLength) {
    return mockBody;
  }

  public void sendData(String data) {
    printWriter.sendData(data);
    printWriter.close();
  }

  public void close() throws IOException {
    this.headerBufferedReader.close();
  }

  public String getSentData() {
    return printWriter.getSentData();
  }

}
