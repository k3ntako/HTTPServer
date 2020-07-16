package com.k3ntako.HTTPServer;

public class ErrorHandler implements ErrorHandlerInterface {
  @Override
  public Response handleError(HTTPError e){
    return generateErrorResponse(e.getStatus(), e);
  }

  @Override
  public Response handleError(Exception e){
    return generateErrorResponse(500, e);
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
}
