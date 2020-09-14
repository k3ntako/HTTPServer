package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapperInterface;

public class PrintWriterWrapperMock implements PrintWriterWrapperInterface {
  private String sentData;
  public boolean closedCalled;

  public PrintWriterWrapperMock() {
    this.sentData = "";
    this.closedCalled = false;
  }

  public void sendData(String data) {
    if (data != null) {
      this.sentData = this.sentData + data + "\n";
    }
  }

  public void close() {
    closedCalled = true;
  }

  public String getSentData() {
    return this.sentData;
  }
}
