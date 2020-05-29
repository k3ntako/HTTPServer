package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapperInterface;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class EchoServer {
  private IOGeneratorInterface ioGenerator;
  private RequestInterface request;

  public EchoServer(IOGeneratorInterface ioGenerator, RequestInterface request) {
    this.ioGenerator = ioGenerator;
    this.request = request;
  }

  public void run(ServerSocketWrapperInterface serverSocket) {
    var clientSocket = serverSocket.accept();

    var io = ioGenerator.generateIO(clientSocket);
    BufferedReader input = (BufferedReader) io.get("bufferedReader");
    PrintWriterWrapperInterface output = (PrintWriterWrapperInterface) io.get("printWriter");

    this.parseHeader(input);
    this.parseBody(input);

    this.sendResponse(output);

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

  private void sendResponse(PrintWriterWrapperInterface output) {
    var response = new Response(request);
    var responseStr = response.createResponse();

    output.sendData(responseStr);
  }

  private void close(BufferedReader input, PrintWriterWrapperInterface output) {
    try {
      input.close();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
