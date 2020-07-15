package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.IOException;
import java.net.Socket;

public class Server {
  private ServerIOInterface serverIO;
  private RequestGeneratorInterface requestGenerator;
  private ServerSocketWrapperInterface serverSocket;
  private Router router;

  public Server(
      ServerIOInterface serverIO,
      RequestGeneratorInterface requestGenerator,
      ServerSocketWrapperInterface serverSocket,
      Router router
  ) {
    this.serverIO = serverIO;
    this.requestGenerator = requestGenerator;
    this.serverSocket = serverSocket;
    this.router = router;
  }

  public void run() throws IOException {
    var clientSocket = serverSocket.accept();
    String responseStr = "";

    try {
      serverIO.init(clientSocket);

      var request = this.requestGenerator.generateRequest(serverIO);
      var response = this.router.routeRequest(request);
      responseStr = response.createResponse();
    } catch (HTTPError e) {
      e.printStackTrace();
      var response = generateErrorResponse(e.getStatus(), e);
      responseStr = response.createResponse();
    } catch (Exception e) {
      e.printStackTrace();
      var response = generateErrorResponse(500, e);
      responseStr = response.createResponse();
    } finally {
      serverIO.sendData(responseStr);
      this.close(clientSocket);
    }
  }

  private Response generateErrorResponse(int status, Exception exception) {
    var response = new Response();
    response.setStatus(status);

    var message = exception.getMessage();
    if(message == null){
      message = exception.getClass().getName();
    }
    response.setBody(message);

    return response;
  }

  private void close(Socket clientSocket) throws IOException {
    serverIO.close();
    clientSocket.close();
  }
}
