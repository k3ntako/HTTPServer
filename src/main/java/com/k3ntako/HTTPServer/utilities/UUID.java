package com.k3ntako.HTTPServer.utilities;

public class UUID implements UUIDInterface {
  @Override
  public String generate() {
    return java.util.UUID.randomUUID().toString();
  }
}
