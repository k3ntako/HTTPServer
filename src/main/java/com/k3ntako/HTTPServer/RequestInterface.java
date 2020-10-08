package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.util.HashMap;

public interface RequestInterface {
  void parseRequest() throws IOException, HTTPError;

  void setRouteParams(HashMap<String, String> routeParams);

  HashMap<String, String> getRouteParams();

  String getRouteParam(String key);

  String getMethod();

  String getRoute();

  String getProtocol();

  HashMap<String, String> getHeaders();

  Object getBody();
}
