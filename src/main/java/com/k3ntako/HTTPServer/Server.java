package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.IOException;
import java.net.Socket;

public class Server {
  final private ServerSocketWrapperInterface serverSocket;
  final private Router router;

  public Server(
      ServerSocketWrapperInterface serverSocket,
      Router router
  ) {
    this.serverSocket = serverSocket;
    this.router = router;
  }

  public void run() {
    try {
      var clientSocket = serverSocket.accept();
      var clientSocketIO = new ClientSocketIO(new RequestBodyParser(), clientSocket);

      Thread object = new Thread(new RequestHandler(
          router,
          new RequestGenerator(),
          new ErrorHandler(),
          clientSocketIO
      ));
      object.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int port() {
    return serverSocket.port();
  }
}
