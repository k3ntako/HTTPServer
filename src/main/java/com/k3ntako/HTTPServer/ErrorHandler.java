package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.utilities.JsonConverter;

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
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter);
    response.setStatus(status);

    var message = exception.getMessage();
    if (message == null) {
      message = exception.getClass().getName();
    }
    response.setBody(message);

    return response;
  }
}
