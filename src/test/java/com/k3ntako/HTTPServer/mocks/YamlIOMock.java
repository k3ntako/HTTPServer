package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.YamlIOInterface;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

public class YamlIOMock implements YamlIOInterface {
  private LinkedHashMap<String, Object> mockConfig;
  private boolean readCalled = false;
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
