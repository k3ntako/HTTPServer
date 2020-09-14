package com.k3ntako.HTTPServer;

public class HTTPError extends Exception {
  final private int status;

  public HTTPError(int status, String message) {
    super(message);
    this.status = status;
  }

  public int getStatus() {
    return this.status;
  }
}
