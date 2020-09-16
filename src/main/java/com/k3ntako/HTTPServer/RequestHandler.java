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

  public String handleRequest(ClientSocketIOInterface clientSocketIO) throws Exception {
    String responseStr;
    try {
      var request = this.requestGenerator.generateRequest(clientSocketIO);
      var response = this.router.routeRequest(request);
      responseStr = response.createResponse();
    } catch (HTTPError httpError) {
      var response = this.errorHandler.handleError(httpError);
      responseStr = response.createResponse();
    } catch (Exception exception) {
      var response = this.errorHandler.handleError(exception);
      responseStr = response.createResponse();
    }

    if (responseStr == null) {
      throw new Exception("Response is null");
    }

    return responseStr;
  }
}
