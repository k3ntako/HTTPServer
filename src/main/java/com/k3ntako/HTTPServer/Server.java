package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapperInterface;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class Server {
  private IOGeneratorInterface ioGenerator;
  private RequestInterface request;
  private ServerSocketWrapperInterface serverSocket;

  public Server(IOGeneratorInterface ioGenerator, RequestInterface request, ServerSocketWrapperInterface serverSocket) {
    this.ioGenerator = ioGenerator;
    this.request = request;
    this.serverSocket = serverSocket;
  }

  public void run() {
    var clientSocket = serverSocket.accept();

    var io = ioGenerator.generateIO(clientSocket);
    BufferedReader input = (BufferedReader) io.get("bufferedReader");
    PrintWriterWrapperInterface output = (PrintWriterWrapperInterface) io.get("printWriter");

    this.parseHeader(input);
    this.parseBody(input);

    this.sendResponse(output);

    this.close(input, output, clientSocket);
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

  private void close(BufferedReader input, PrintWriterWrapperInterface output, Socket clientSocket) {
    try {
      input.close();
      output.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop(){
    this.serverSocket.close();
  }
}
