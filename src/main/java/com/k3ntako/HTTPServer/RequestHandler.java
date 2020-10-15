package com.k3ntako.HTTPServer;

public class RequestHandler implements Runnable {
  final private Router router;
  final private RequestGeneratorInterface requestGenerator;
  final private ErrorHandlerInterface errorHandler;
  final private ClientSocketIOInterface clientSocketIO;

  public RequestHandler(
      Router router,
      RequestGeneratorInterface requestGenerator,
      ErrorHandlerInterface errorHandler,
      ClientSocketIOInterface clientSocketIO
  ) {
    this.router = router;
    this.requestGenerator = requestGenerator;
    this.errorHandler = errorHandler;
    this.clientSocketIO = clientSocketIO;
  }

  @Override
  public void run() {
    try {
      byte[] responseBytes;
      try {
        var request = this.requestGenerator.generateRequest(clientSocketIO);
        var response = this.router.routeRequest(request);
        responseBytes = response.createResponse();
      } catch (HTTPError httpError) {
        var response = this.errorHandler.handleError(httpError);
        responseBytes = response.createResponse();
      } catch (Exception exception) {
        var response = this.errorHandler.handleError(exception);
        responseBytes = response.createResponse();
      }

      if (responseBytes == null) {
        throw new Exception("Response is null");
      }

      clientSocketIO.sendData(responseBytes);
      clientSocketIO.close();

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
