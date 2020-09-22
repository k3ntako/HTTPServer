package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface ControllerMethodInterface {
  ResponseInterface getResponse(RequestInterface request, ResponseInterface response) throws IOException, HTTPError;
}
