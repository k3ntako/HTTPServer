package com.k3ntako.HTTPServer;

public interface JsonIOInterface {
  <T> T fromJson(String jsonStr, Class<T> type);

  String toJson(Object json);
}
