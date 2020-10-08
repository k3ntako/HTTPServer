package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ClientSocketIOInterface;

import java.io.*;
import java.net.Socket;

public class ClientSocketIOMock implements ClientSocketIOInterface {
  final private byte[] mockBody;
  final private BufferedReader headerBufferedReader;
  private ClientOutputStreamMock clientOutputStreamMock;

  public ClientSocketIOMock(String mockHeader) {
    this.headerBufferedReader = new BufferedReader(new StringReader(mockHeader));
    this.mockBody = "".getBytes();
  }

  public ClientSocketIOMock(String mockHeader, String mockBody) {
    this.headerBufferedReader = new BufferedReader(new StringReader(mockHeader));
    this.mockBody = mockBody.getBytes();
  }

  public ClientSocketIOMock(String mockHeader, byte[] mockBody) {
    this.headerBufferedReader = new BufferedReader(new StringReader(mockHeader));
    this.mockBody = mockBody;
  }

  public void init(Socket socket) {
    // Socket is ignored
    clientOutputStreamMock = new ClientOutputStreamMock();
  }

  public String readLine() throws IOException {
    return headerBufferedReader.readLine();
  }

  @Override
  public byte[] parseBody(int contentLength) {
    return mockBody;
  }

  public void sendData(byte[] data) {
    clientOutputStreamMock.sendData(data);
    clientOutputStreamMock.close();
  }

  public void close() throws IOException {
    this.headerBufferedReader.close();
  }

  public byte[] getSentData() {
    return clientOutputStreamMock.getSentData();
  }

}
