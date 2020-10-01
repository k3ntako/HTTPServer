package com.k3ntako.HTTPServer.utilities;

public interface JsonConverterInterface {
  <T> T fromJson(String jsonStr, Class<T> type);

  String toJson(Object json);
}
