package com.k3ntako.HTTPServer;

import com.google.gson.Gson;

public class ErrorHandler implements ErrorHandlerInterface {
  @Override
  public ResponseInterface handleError(HTTPError e) throws HTTPError {
    return generateErrorResponse(e.getStatus(), e);
  }

  @Override
  public ResponseInterface handleError(Exception e) throws HTTPError {
    return generateErrorResponse(500, e);
  }

  private ResponseInterface generateErrorResponse(int status, Exception exception) throws HTTPError {
    var jsonIO = new JsonIO(new Gson());
    var response = new Response(jsonIO);
    response.setStatus(status);

    var message = exception.getMessage();
    if (message == null) {
      message = exception.getClass().getName();
    }
    response.setBody(message);

    return response;
  }
}
