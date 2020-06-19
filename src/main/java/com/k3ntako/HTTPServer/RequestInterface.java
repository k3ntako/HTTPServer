package com.k3ntako.HTTPServer;

import java.util.HashMap;

public interface RequestInterface {
  void parseRequest();

  String getMethod();

  String getRoute();

  String getProtocol();

  HashMap<String, String> getHeaders();

  String getBody();
}
