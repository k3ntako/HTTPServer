package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import com.k3ntako.HTTPServer.utilities.JsonConverter;

import java.io.OutputStream;
import java.io.PrintStream;

public class ErrorHandler implements ErrorHandlerInterface {
  public ErrorHandler() {
    var enableErrLogging = System.getenv().get("ERR_LOGGING");

    if(enableErrLogging != null && enableErrLogging.equals("true")){
      disableErrLogging();
    }
  }

  private void disableErrLogging(){
    System.setErr(new PrintStream(new OutputStream() {
      public void write(int b) {} // Ignores standard error
    }));
  }

  @Override
  public ResponseInterface handleError(HTTPError e) throws HTTPError {
    return generateErrorResponse(e.getStatus(), e);
  }

  @Override
  public ResponseInterface handleError(Exception e) throws HTTPError {
    return generateErrorResponse(500, e);
  }

  private ResponseInterface generateErrorResponse(int status, Exception exception) throws HTTPError {
    exception.printStackTrace();
    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new MimeTypes());
    response.setStatus(status);

    var message = exception.getMessage();
    if (message == null) {
      message = exception.getClass().getName();
    }
    response.setBody(message);

    return response;
  }
}
