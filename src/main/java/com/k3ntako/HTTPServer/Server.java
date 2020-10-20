package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class Server {
  final private ExecutorService threadPool;
  final private ServerSocketWrapperInterface serverSocket;
  final private Router router;

  public Server(
      ExecutorService threadPool,
      ServerSocketWrapperInterface serverSocket,
      Router router
  ) {
    this.threadPool = threadPool;
    this.serverSocket = serverSocket;
    this.router = router;
  }

  public void run() {
    try {
      var clientSocket = serverSocket.accept();
      var clientSocketIO = new ClientSocketIO(new RequestBodyParser(), clientSocket);

      threadPool.execute(new RequestHandler(
          router,
          new RequestGenerator(),
          new ErrorHandler(),
          clientSocketIO
      ));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int port() {
    return serverSocket.port();
  }
}
