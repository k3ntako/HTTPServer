package com.k3ntako.HTTPServer;

public class RequestHandler {
  private Router router;
  private RequestGeneratorInterface requestGenerator;
  private ErrorHandlerInterface errorHandler;

  public RequestHandler(Router router, RequestGeneratorInterface requestGenerator) {
    this.router = router;
    this.requestGenerator = requestGenerator;
  }

  public String handleRequest(ServerIOInterface serverIO) throws Exception {
    try {
      var request = this.requestGenerator.generateRequest(serverIO);
      var response = this.router.routeRequest(request);
      return response.createResponse();
    } catch (Exception e) {
      return this.handleError(e);
    }
  }

  public void useErrorHandler(ErrorHandlerInterface errorHandler) {
    this.errorHandler = errorHandler;
  }

  private String handleError(Exception e) throws Exception {
    if(this.errorHandler == null){
      throw e;
    }

    var response = this.errorHandler.handleError(e);
    return response.createResponse();
  }
}
