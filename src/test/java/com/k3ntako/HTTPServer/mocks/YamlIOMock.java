package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.YamlIOInterface;

import java.util.LinkedHashMap;

public class YamlIOMock implements YamlIOInterface {
  final private LinkedHashMap<String, Object> mockConfig;
  final private boolean readCalled = false;

  public YamlIOMock(LinkedHashMap<String, Object> mockConfig) {
    this.mockConfig = mockConfig;
  }

  @Override
  public LinkedHashMap<String, Object> read(String fileName) {
    return mockConfig;
  }

  public boolean wasReadCalled() {
    return readCalled;
  }
}
