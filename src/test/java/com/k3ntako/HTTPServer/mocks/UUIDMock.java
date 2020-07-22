package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.UUIDInterface;

public class UUIDMock implements UUIDInterface {
  private String mockReturn;
  public UUIDMock(String mockReturn) {
    this.mockReturn = mockReturn;
  }


  @Override
  public String generate() {
    return mockReturn;
  }
}
