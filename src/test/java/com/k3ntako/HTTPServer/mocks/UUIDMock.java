package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.utilities.UUIDInterface;

public class UUIDMock implements UUIDInterface {
  private String mockReturn;
  private final String defaultUUID = "8d142d80-565f-417d-8334-a8a19caadadb";

  public UUIDMock() {
    this.mockReturn = defaultUUID;
  }

  public UUIDMock(String mockReturn) {
    this.mockReturn = mockReturn;
  }

  @Override
  public String generate() {
    return mockReturn;
  }

  public String getDefaultUUID() {
    return defaultUUID;
  }
}
