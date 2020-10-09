package com.k3ntako.HTTPServer;

public class RequestHandler {
  final private Router router;
  final private RequestGeneratorInterface requestGenerator;
  final private ErrorHandlerInterface errorHandler;

  public RequestHandler(
      Router router,
      RequestGeneratorInterface requestGenerator,
      ErrorHandlerInterface errorHandler
  ) {
    this.router = router;
    this.requestGenerator = requestGenerator;
    this.errorHandler = errorHandler;
  }

  public byte[] handleRequest(ClientSocketIOInterface clientSocketIO) throws Exception {
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

    return responseBytes;
  }
}
