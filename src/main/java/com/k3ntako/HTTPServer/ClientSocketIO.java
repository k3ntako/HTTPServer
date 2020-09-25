package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapper;

import java.io.*;
import java.net.Socket;

public class ClientSocketIO implements ClientSocketIOInterface {
  private Socket clientSocket;
  private RequestBodyParserInterface requestBodyParser;
  private BufferedReader bufferedReader;
  private PrintWriterWrapper printWriter;
  private String contentTypeCategory;
  private String contentSubtype;

  public ClientSocketIO(RequestBodyParserInterface requestBodyParser) {
    this.requestBodyParser = requestBodyParser;
  }


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

  public Object parseBody(String contentType, int contentLength) throws IOException {
    final var contentTypeArr = contentType.split("/");
    contentTypeCategory = contentTypeArr[0].toLowerCase();
    contentSubtype = contentTypeArr[1].toLowerCase();

    return requestBodyParser.parseBody(bufferedReader, clientSocket, contentTypeCategory, contentLength);
  }


  public void sendData(String data) {
    printWriter.sendData(data);
  }

  public void close() throws IOException {
    bufferedReader.close();
    printWriter.close();
  }

}
