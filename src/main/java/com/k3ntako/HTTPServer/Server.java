package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapperInterface;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class Server {
  private IOGeneratorInterface ioGenerator;
  private RequestHandlerInterface requestHandler;
  private ServerSocketWrapperInterface serverSocket;
  private Router router;

  public Server(
          IOGeneratorInterface ioGenerator,
          RequestHandlerInterface requestHandler,
          ServerSocketWrapperInterface serverSocket,
          Router router
  ) {
    this.ioGenerator = ioGenerator;
    this.requestHandler = requestHandler;
    this.serverSocket = serverSocket;
    this.router = router;
  }

  public void run() {
    var clientSocket = serverSocket.accept();

    var io = ioGenerator.generateIO(clientSocket);
    BufferedReader input = (BufferedReader) io.get("bufferedReader");
    PrintWriterWrapperInterface output = (PrintWriterWrapperInterface) io.get("printWriter");

    var request = this.requestHandler.handleRequest(input);
    var route = this.router.routeRequest(request);
    var response = route.createResponse();
    output.sendData(response);

    this.close(input, output, clientSocket);
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
}
