package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.UUIDInterface;

public class UUIDMock implements UUIDInterface {
  private String mockReturn;
  public UUIDMock() {
    this.mockReturn = "8d142d80-565f-417d-8334-a8a19caadadb";
  }

  public UUIDMock(String mockReturn) {
    this.mockReturn = mockReturn;
  }

  @Override
  public String generate() {
    return mockReturn;
  }
}
