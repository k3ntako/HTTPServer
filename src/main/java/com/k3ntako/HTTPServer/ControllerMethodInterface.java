package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface ControllerMethodInterface {
  Response getResponse(RequestInterface request) throws IOException, HTTPError;
}
