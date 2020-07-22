package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface ControllerInterface {
  Response getResponse(RequestInterface request) throws IOException, HTTPError;
}
