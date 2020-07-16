package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.IOException;
import java.net.Socket;

public class Server {
  private ServerIOInterface serverIO;
  private RequestHandler requestHandler;
  private ServerSocketWrapperInterface serverSocket;
  private Router router;

  public Server(
      ServerIOInterface serverIO,
      RequestHandler requestHandler,
      ServerSocketWrapperInterface serverSocket,
      Router router
  ) {
    this.serverIO = serverIO;
    this.requestHandler = requestHandler;
    this.serverSocket = serverSocket;
    this.router = router;
  }

  public void run() throws Exception {
    var clientSocket = serverSocket.accept();

    serverIO.init(clientSocket);

    var responseStr = requestHandler.handleRequest(serverIO);

    serverIO.sendData(responseStr);
    this.close(clientSocket);
  }

  private void close(Socket clientSocket) throws IOException {
    serverIO.close();
    clientSocket.close();
  }
}
