package com.k3ntako.HTTPServer;

public class ErrorHandler implements ErrorHandlerInterface {

  @Override
  public Response handleError(Exception e) {
    var status = this.getStatus(e);
    return generateErrorResponse(status, e);
  }

  private Response generateErrorResponse(int status, Exception exception) {
    var response = new Response();
    response.setStatus(status);

    var message = exception.getMessage();
    if (message == null) {
      message = exception.getClass().getName();
    }
    response.setBody(message);

    return response;
  }

  private int getStatus(Exception e) {
    if (e instanceof HTTPError) {
      return ((HTTPError) e).getStatus();
    }

    return 500;
  }
}
