package com.k3ntako.HTTPServer;

import java.io.BufferedReader;
import java.util.HashMap;

public interface RequestInterface {
  void parseRequest(BufferedReader bufferedReader);

  void parseBody(BufferedReader bufferedReader, int contentLength);

  String getMethod();

  String getRoute();

  String getProtocol();

  HashMap<String, String> getHeaders();

  String getBody();
}
