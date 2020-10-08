package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ClientOutputStream;

import java.io.*;
import java.net.Socket;

public class ClientSocketIO implements ClientSocketIOInterface {
  private Socket clientSocket;
  final private RequestBodyParserInterface requestBodyParser;
  private BufferedReader bufferedReader;
  private ClientOutputStream clientOutputStream;

  public ClientSocketIO(RequestBodyParserInterface requestBodyParser) {
    this.requestBodyParser = requestBodyParser;
  }


  public void init(Socket clientSocket) throws IOException {
    this.clientSocket = clientSocket;

    var inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
    bufferedReader = new BufferedReader(inputStreamReader);

    var outputStream = clientSocket.getOutputStream();
    clientOutputStream = new ClientOutputStream(outputStream);
  }

  public String readLine() throws IOException {
    return bufferedReader.readLine();
  }

  public byte[] parseBody(String contentType, int contentLength) throws IOException {
    final var contentTypeArr = contentType.split("/");
    final var contentTypeCategory = contentTypeArr[0].toLowerCase();

    return requestBodyParser.parseBody(bufferedReader, clientSocket, contentTypeCategory, contentLength);
  }


  public void sendData(byte[] data) throws IOException {
    clientOutputStream.sendData(data);
  }

  public void close() throws IOException {
    bufferedReader.close();
    clientOutputStream.close();
  }

}
