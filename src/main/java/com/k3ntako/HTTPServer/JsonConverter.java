package com.k3ntako.HTTPServer;

import com.google.gson.Gson;

public class JsonConverter implements JsonConverterInterface {
  final private Gson gson;

  public JsonConverter(Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T> T fromJson(String jsonStr, Class<T> type) {
    return gson.fromJson(jsonStr, type);
  }

  @Override
  public String toJson(Object json) {
    return gson.toJson(json);
  }
}
