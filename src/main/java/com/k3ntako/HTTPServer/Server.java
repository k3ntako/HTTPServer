package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.IOException;
import java.net.Socket;

public class Server {
  private ServerIOInterface serverIO;
  private RequestHandlerInterface requestHandler;
  private ServerSocketWrapperInterface serverSocket;
  private Router router;

  public Server(
          ServerIOInterface serverIO,
          RequestHandlerInterface requestHandler,
          ServerSocketWrapperInterface serverSocket,
          Router router
  ) {
    this.serverIO = serverIO;
    this.requestHandler = requestHandler;
    this.serverSocket = serverSocket;
    this.router = router;
  }

  public void run() throws IOException {
    var clientSocket = serverSocket.accept();

    serverIO.init(clientSocket);

    var request = this.requestHandler.handleRequest(serverIO);
    var route = this.router.routeRequest(request);
    var response = route.createResponse();
    serverIO.sendData(response);

    this.close(clientSocket);
  }

  private void close(Socket clientSocket) {
    try {
      serverIO.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
