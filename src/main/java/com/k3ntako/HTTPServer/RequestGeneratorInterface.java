package com.k3ntako.HTTPServer;

import java.io.IOException;

public interface RequestGeneratorInterface {
  RequestInterface generateRequest(ServerIOInterface serverIO) throws IOException, HTTPError;
}
