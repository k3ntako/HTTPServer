package com.k3ntako.HTTPServer;

public class RequestHandler {
  private Router router;
  private RequestGeneratorInterface requestGenerator;
  private ErrorHandlerInterface errorHandler;

  public RequestHandler(
      Router router,
      RequestGeneratorInterface requestGenerator,
      ErrorHandlerInterface errorHandler
  ) {
    this.router = router;
    this.requestGenerator = requestGenerator;
    this.errorHandler = errorHandler;
  }

  public String handleRequest(ServerIOInterface serverIO) throws Exception {
    Response response;
    try {
      var request = this.requestGenerator.generateRequest(serverIO);
      response = this.router.routeRequest(request);
    } catch (HTTPError httpError) {
      response = this.errorHandler.handleError(httpError);
    } catch (Exception exception) {
      response = this.errorHandler.handleError(exception);
    }

    if(response == null){
      throw new Exception("Response is null");
    }

    return response.createResponse();
  }
}
