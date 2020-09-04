package com.k3ntako.HTTPServer;

import com.google.gson.Gson;

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
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setStatus(status);

    var message = exception.getMessage();
    if(message == null){
      message = exception.getClass().getName();
    }
    response.setBody(message);

    return response;
  }
}
