package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.util.HashMap;

public interface RequestInterface {
  void parseRequest() throws IOException;

  void setParams(HashMap<String, String> params);

  HashMap<String, String> getParams();

  String getMethod();

  String getRoute();

  String getProtocol();

  HashMap<String, String> getHeaders();

  String getBody();
}
