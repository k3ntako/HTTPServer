package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.wrappers.ClientOutputStreamInterface;

public class ClientOutputStreamMock implements ClientOutputStreamInterface {
  private byte[] sentData = new byte[]{};
  public boolean closedCalled;

  public ClientOutputStreamMock() {
    this.closedCalled = false;
  }

  public void sendData(byte[] data) {
    if (data != null) {
      this.sentData = concatByteArrays(this.sentData, data);
    }
  }

  public void close() {
    closedCalled = true;
  }

  public byte[] getSentData() {
    return this.sentData;
  }

  private byte[] concatByteArrays(byte[] first, byte[] second) {
    byte[] combined = new byte[first.length + second.length];
    System.arraycopy(first, 0, combined, 0, first.length);
    System.arraycopy(second, 0, combined, first.length, second.length);
    return combined;
  }

}
