package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ClientSocketIOInterface;

import java.io.*;
import java.net.Socket;

public class ClientSocketIOMock implements ClientSocketIOInterface {
  private Socket clientSocket;
  private BufferedReader bufferedReader;
  private PrintWriterWrapperMock printWriter;

  public ClientSocketIOMock(String mockClientInput) {
    clientSocket = new SocketMock(mockClientInput.getBytes());
  }

  public void init(Socket socket) throws IOException {
    if (clientSocket == null) {
      clientSocket = socket;
    }

    var inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
    bufferedReader = new BufferedReader(inputStreamReader);
    printWriter = new PrintWriterWrapperMock();
  }

  public String readLine() throws IOException {
    return bufferedReader.readLine();
  }

  public char read() throws IOException {
    return (char) bufferedReader.read();
  }

  @Override
  public String readTextBody(int contentLength) throws IOException {
    var bodyStr = "";
    char character;

    while (bodyStr.length() < contentLength) {
      character = (char) bufferedReader.read();
      bodyStr = bodyStr.concat(String.valueOf(character));
    }
    return bodyStr;
  }

  @Override
  public byte[] readBinaryBody(int contentLength) {
    return new byte[0];
  }

  public void sendData(String data) {
    printWriter.sendData(data);
    printWriter.close();
  }

  public void close() throws IOException {
    this.bufferedReader.close();
  }

  public String getSentData() {
    return printWriter.getSentData();
  }

}
