package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.util.HashMap;

public interface RequestInterface {
  void parseRequest() throws IOException;

  String getMethod();

  String getRoute();

  String getProtocol();

  HashMap<String, String> getHeaders();

  String getBody();
}
