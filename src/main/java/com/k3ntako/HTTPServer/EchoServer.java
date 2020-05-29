package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EchoServer {
  private IOGeneratorInterface ioGenerator;
  private Request request;

  public EchoServer(IOGeneratorInterface ioGenerator, Request request) {
    this.ioGenerator = ioGenerator;
    this.request = request;
  }

  public void run(ServerSocketWrapperInterface serverSocket) {
    var clientSocket = serverSocket.accept();

    var io = ioGenerator.generateIO(clientSocket);
    BufferedReader input = (BufferedReader) io.get("bufferedReader");
    PrintWriter output = (PrintWriter) io.get("printWriter");

    this.parseHeader(input);
    this.parseBody(input);

    this.close(input, output);
  }

  private void parseHeader(BufferedReader input) {
    request.parseRequest(input);
  }

  private void parseBody(BufferedReader input) {
    int contentLength = 0;
    if (request.getHeaders().containsKey("Content-Length")) {
      contentLength = Integer.parseInt(request.getHeaders().get("Content-Length"));
    }

    if (contentLength > 0) {
      request.parseBody(input, contentLength);
    }
  }

  private void close(BufferedReader input, PrintWriter output) {
    try {
      input.close();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
