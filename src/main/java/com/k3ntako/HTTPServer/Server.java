package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.IOException;
import java.net.Socket;

public class Server {
  final private ClientSocketIOInterface clientSocketIO;
  final private RequestHandler requestHandler;
  final private ServerSocketWrapperInterface serverSocket;

  public Server(
      ClientSocketIOInterface clientSocketIO,
      RequestHandler requestHandler,
      ServerSocketWrapperInterface serverSocket
  ) {
    this.clientSocketIO = clientSocketIO;
    this.requestHandler = requestHandler;
    this.serverSocket = serverSocket;
  }

  public void run() throws Exception {
    var clientSocket = serverSocket.accept();

    clientSocketIO.init(clientSocket);

    var responseStr = requestHandler.handleRequest(clientSocketIO);

    clientSocketIO.sendData(responseStr);
    this.close(clientSocket);
  }

  private void close(Socket clientSocket) throws IOException {
    clientSocketIO.close();
    clientSocket.close();
  }
}
