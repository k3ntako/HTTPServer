package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.utilities.JsonConverterInterface;

import java.lang.reflect.InvocationTargetException;

public class JsonConverterMock implements JsonConverterInterface {
  public String fromJsonStr;
  public Class fromJsonType;
  public Object toJsonArg;

  @Override
  public <T> T fromJson(String jsonStr, Class<T> type) {
    this.fromJsonStr = jsonStr;
    this.fromJsonType = type;

    try {
      return type.getConstructor().newInstance();
    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public String toJson(Object json) {
    this.toJsonArg = json;
    return "{\"id\":\"mock_id\"}";
  }
}
