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
    byte[] responseByte;
    try {
      var request = this.requestGenerator.generateRequest(clientSocketIO);
      var response = this.router.routeRequest(request);
      responseByte = response.createResponse();
    } catch (HTTPError httpError) {
      var response = this.errorHandler.handleError(httpError);
      responseByte = response.createResponse();
    } catch (Exception exception) {
      var response = this.errorHandler.handleError(exception);
      responseByte = response.createResponse();
    }

    if (responseByte == null) {
      throw new Exception("Response is null");
    }

    return responseByte;
  }
}
